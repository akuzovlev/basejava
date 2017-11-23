package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;
import ru.javawebinar.basejava.util.SqlHelper;

import java.sql.*;
import java.util.List;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        SqlHelper.executeHelper(connectionFactory, "execute", "DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return SqlHelper.executeHelper(connectionFactory, "get", "SELECT * FROM resume r WHERE r.uuid =?", uuid).get(0);
    }

    @Override
    public void update(Resume r) {
        SqlHelper.executeHelper(connectionFactory, "execute", "UPDATE resume SET full_name = ? WHERE uuid = ?", r.getFullName(), r.getUuid());
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
            SqlHelper.executeHelper(connectionFactory, "execute", "INSERT INTO resume (uuid, full_name) VALUES (?,?)", r.getUuid(), r.getFullName());
        }
    }

    @Override
    public void delete(String uuid) {
        SqlHelper.executeHelper(connectionFactory, "delete", "DELETE FROM resume WHERE uuid=?", uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        return SqlHelper.executeHelper(connectionFactory, "getAll", "SELECT * FROM resume");
    }

    @Override
    public int size() {
        return SqlHelper.executeHelper(connectionFactory, "getAll", "SELECT * FROM resume").size();

    }
}