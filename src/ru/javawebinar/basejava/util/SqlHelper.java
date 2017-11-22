package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlHelper {

    public static void executeHelper(ConnectionFactory connectionFactory, String statement,String...params) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(statement)) {
            if (params != null) {
                for (int i = 1; i <= params.length; i++) {
                    ps.setString(i, params[i-1]);
                }
            }
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}
