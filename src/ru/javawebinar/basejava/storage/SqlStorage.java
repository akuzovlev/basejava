package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ExecuteCode;
import ru.javawebinar.basejava.sql.SqlHelper;

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
        helper.executeHelper("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        Resume rez = (Resume) helper.executeHelper("SELECT * FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
        return rez;
    }

    @Override
    public void update(Resume r) {
        helper.executeHelper("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            int result = ps.executeUpdate();
            if (result == 0) {
                throw new NotExistStorageException(r.getUuid() + "Not exists");
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        try {
            helper.executeHelper("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
                return null;
            });
        } catch (StorageException e) {
            throw new ExistStorageException(r.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        helper.executeHelper("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            int result = ps.executeUpdate();
            if (result == 0) {
                throw new NotExistStorageException(uuid + "Not exists");
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> rezult = new ArrayList<>();
        helper.executeHelper("SELECT * FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rezult.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            return null;
        });
        return rezult;
    }

    @Override
    public int size() {
        List<Integer> size = new ArrayList<>();
        helper.executeHelper("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            size.add(Integer.parseInt(rs.getString(1)));
            return null;
        });
        return size.get(0);
    }
}