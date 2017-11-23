package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlHelper {

    public static List<Resume> executeHelper(ConnectionFactory connectionFactory, String action, String statement, String... params) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(statement)) {
            if (params != null) {
                for (int i = 1; i <= params.length; i++) {
                    ps.setString(i, params[i - 1]);
                }
            }
            return doJob(ps, action, statement, params);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    private static List<Resume> doJob(PreparedStatement ps, String action, String statement, String... params) throws SQLException {
        List<Resume> result = new ArrayList<>();
        switch (action) {
            case "execute":
                ps.execute();
                return null;
            case "get":
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(params[0]);
                }
                result.add(new Resume(params[0], rs.getString("full_name")));
                break;
            case "delete":
                int rez = ps.executeUpdate();
                if (rez == 0) {
                    throw new NotExistStorageException(params[0] + "Not exists");
                }
                break;
            case "getAll":
                ResultSet rsAll = ps.executeQuery();
                while (rsAll.next()) {
                    result.add(new Resume(rsAll.getString("uuid"), rsAll.getString("full_name")));
                }
                break;
        }
        return result;
    }


}
