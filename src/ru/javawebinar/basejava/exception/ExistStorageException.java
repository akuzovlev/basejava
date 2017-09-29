package ru.javawebinar.basejava.exception;

/**
 * Created by kuzovlea on 29.09.2017.
 */


public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Resume " + uuid + " already exist", uuid);
    }
}
