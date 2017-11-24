package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;
import ru.javawebinar.basejava.util.SqlHelper;

import java.sql.*;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper helper;
    private final String dbUrl = Config.get().getDbUrl();
    private final String dbUser = Config.get().getDbUser();
    private final String dbPassword = Config.get().getDbPassword();

    public SqlStorage() {
        this.helper = new SqlHelper(dbUrl,dbUser,dbPassword);
    }

    @Override
    public void clear() {
       helper.executeHelper("execute", "DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return  helper.executeHelper("get", "SELECT * FROM resume r WHERE r.uuid =?", uuid).get(0);
    }

    @Override
    public void update(Resume r) {
        helper.executeHelper( "execute", "UPDATE resume SET full_name = ? WHERE uuid = ?", r.getFullName(), r.getUuid());
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
            helper.executeHelper( "execute", "INSERT INTO resume (uuid, full_name) VALUES (?,?)", r.getUuid(), r.getFullName());
        }
    }

    @Override
    public void delete(String uuid) {
        helper.executeHelper( "delete", "DELETE FROM resume WHERE uuid=?", uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        return  helper.executeHelper( "getAll", "SELECT * FROM resume");
    }

    @Override
    public int size() {
        return  helper.executeHelper( "getAll", "SELECT * FROM resume").size();

    }
}