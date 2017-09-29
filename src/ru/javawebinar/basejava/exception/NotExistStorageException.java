package ru.javawebinar.basejava.exception;

/**
 * Created by kuzovlea on 29.09.2017.
 */

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Resume " + uuid + " not exist", uuid);
    }
}
