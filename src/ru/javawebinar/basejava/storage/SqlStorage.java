package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        return sqlHelper.execute("" +
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
                        String contactType = rs.getString("type");
                        if (contactType != null) {
                            ContactType type = ContactType.valueOf(contactType);
                            String value = rs.getString("value");
                            r.addContact(type, value);
                        }
                    } while (rs.next());

                    return r;
                });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                        ps.setString(1, r.getFullName());
                        ps.setString(2, r.getUuid());
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(r.getUuid());
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE contact SET type = ?, value = ? WHERE resume_uuid = ?")) {
                        if (r.getContacts().isEmpty()) {
                            sqlHelper.execute("DELETE FROM contact WHERE resume_uuid = ?", ps1 -> {
                                ps1.setString(1, r.getUuid());
                                ps1.execute();
                                return null;
                            });
                        } else {
                            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                                ps.setString(1, e.getKey().name());
                                ps.setString(2, e.getValue());
                                ps.setString(3, r.getUuid());
                                ps.addBatch();
                            }
                            ps.executeBatch();
                        }
                    }
                    return null;
                }
        );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                        for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                            ps.setString(1, r.getUuid());
                            ps.setString(2, e.getKey().name());
                            ps.setString(3, e.getValue());
                            ps.addBatch();
                        }
                        ps.executeBatch();
                    }
                    return null;
                }
        );
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
        return sqlHelper.execute("SELECT * FROM resume r LEFT JOIN\n" +
                "(SELECT  resume_uuid, array_to_string(array_agg(type),',') AS type_arr, \n" +
                "    array_to_string(array_agg(value),',') AS value_arr\n" +
                "FROM  contact c \n" +
                "GROUP BY resume_uuid) rez ON r.uuid = rez.resume_uuid;", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                Resume r = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                if (rs.getString("type_arr") != null) {
                String[] contactTypeArray = rs.getString("type_arr").split(",");
                String[] valueArray = rs.getString("value_arr").split(",");

                    for (int i = 0; i < contactTypeArray.length; i++) {
                        ContactType type = ContactType.valueOf(contactTypeArray[i]);
                        String value = valueArray[i];
                        r.addContact(type, value);
                    }
                }


                resumes.add(r);
            }
                return resumes;
            });
        }

        @Override
        public int size () {
            return sqlHelper.execute("SELECT count(*) FROM resume", st -> {
                ResultSet rs = st.executeQuery();
                return rs.next() ? rs.getInt(1) : 0;
            });
        }
    }