package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface ExecuteCode {
    List<Resume> execute(PreparedStatement ps) throws SQLException;
}
