package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;


public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {

        Resume resume = sqlHelper.execute("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(rs, r);
                    } while (rs.next());
                    return r;
                });

        sqlHelper.execute("" +
                        "    SELECT * FROM section s " +
                        "     WHERE s.resume_uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        addSection(rs, resume);
                    }

                    return null;
                });

        return resume;
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() != 1) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(conn, r);
            insertContact(conn, r);
            deleteSections(conn, r);
            insertSection(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContact(conn, r);
            insertSection(conn, r);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> map = new LinkedHashMap<>();
        sqlHelper.execute("" +
                "   SELECT * FROM resume r\n" +
                "ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                map.put(uuid, new Resume(uuid, rs.getString("full_name")));
            }
            return null;
        });

        sqlHelper.execute("" +
                "   SELECT * FROM contact \n", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("resume_uuid");
                Resume resume = map.get(uuid);
                addContact(rs, resume);
            }
            return null;
        });


        sqlHelper.execute("" +
                "   SELECT * FROM section \n", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("resume_uuid");
                Resume resume = map.get(uuid);
                addSection(rs, resume);
            }
            return null;
        });

        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", st -> {
            ResultSet rs = st.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSection(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                SectionType type = e.getKey();
                ps.setString(2, type.name());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        ps.setString(3, ((TextSection) e.getValue()).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        StringBuilder sb = new StringBuilder();
                        for (String s : ((ListSection) e.getValue()).getItems()) {
                            sb.append(s);
                            sb.append("\n");
                        }
                        ps.setString(3, sb.toString());
                        break;
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Connection conn, Resume r) {
        sqlHelper.execute("DELETE  FROM contact WHERE resume_uuid=?", ps -> {
            ps.setString(1, r.getUuid());
            ps.execute();
            return null;
        });
    }

    private void deleteSections(Connection conn, Resume r) {
        sqlHelper.execute("DELETE  FROM section WHERE resume_uuid=?", ps -> {
            ps.setString(1, r.getUuid());
            ps.execute();
            return null;
        });
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            r.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        SectionType type = SectionType.valueOf(rs.getString("type"));
        if (value != null) {
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    r.addSection(type, new TextSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    r.addSection(type, new ListSection(value.split("\n")));
                    break;
            }

        }
    }
}