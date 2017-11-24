package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.util.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper helper;

    public SqlStorage() {
        helper = new SqlHelper(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword());
    }

    @Override
    public void clear() {
        helper.executeHelper("DELETE FROM resume", new ExecuteCode() {
            @Override
            public List<Resume> execute(PreparedStatement ps) throws SQLException {
                ps.execute();
                return null;
            }
        });
    }

    @Override
    public Resume get(String uuid) {
        List<Resume> rez = helper.executeHelper("SELECT * FROM resume r WHERE r.uuid =?", new ExecuteCode() {
            @Override
            public List<Resume> execute(PreparedStatement ps) throws SQLException {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                List<Resume> result = new ArrayList<>();
                result.add(new Resume(uuid, rs.getString("full_name")));
                return result;
            }
        });
        return rez.get(0);
    }

    @Override
    public void update(Resume r) {
        helper.executeHelper("UPDATE resume SET full_name = ? WHERE uuid = ?", new ExecuteCode() {
            @Override
            public List<Resume> execute(PreparedStatement ps) throws SQLException {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                ps.execute();
                return null;
            }
        });
    }

    @Override
    public void save(Resume r) {
        Resume resume = null;
        try {
            resume = get(r.getUuid());
        } catch (StorageException e) {
        }
        if (resume != null) {
            throw new ExistStorageException(r.getUuid());
        } else {
            helper.executeHelper("INSERT INTO resume (uuid, full_name) VALUES (?,?)", new ExecuteCode() {
                @Override
                public List<Resume> execute(PreparedStatement ps) throws SQLException {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, r.getFullName());
                    ps.execute();
                    return null;
                }
            });
        }
    }

    @Override
    public void delete(String uuid) {
        helper.executeHelper("DELETE FROM resume WHERE uuid=?", new ExecuteCode() {
            @Override
            public List<Resume> execute(PreparedStatement ps) throws SQLException {
                ps.setString(1, uuid);
                int result = ps.executeUpdate();
                if (result == 0) {
                    throw new NotExistStorageException(uuid + "Not exists");
                }
                return null;
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> rezult = new ArrayList<>();
        helper.executeHelper("SELECT * FROM resume", new ExecuteCode() {
            @Override
            public List<Resume> execute(PreparedStatement ps) throws SQLException {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    rezult.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
                return null;
            }
        });
        return rezult;
    }

    @Override
    public int size() {
        List<Integer> size = new ArrayList<>();
        helper.executeHelper("SELECT count(*) FROM resume", new ExecuteCode() {
            @Override
            public List<Resume> execute(PreparedStatement ps) throws SQLException {
                ResultSet rs = ps.executeQuery();
                rs.next();
                size.add(Integer.parseInt(rs.getString(1)));
                return null;
            }
        });
        return size.get(0);
    }
}