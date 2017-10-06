package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

/**
 * Created by KuzovleA on 06.10.2017.
 */
public abstract class AbstractStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;

    public abstract int size();

    public abstract void clear();

    public void update(Resume r) {
        if (!isExists(r.getUuid())) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            updateElement(r);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public abstract Resume[] getAll();

    public void save(Resume r) {
        if (isExists(r.getUuid())) {
            throw new ExistStorageException(r.getUuid());
        } else if (size() >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            insertElement(r);
        }
    }

    public void delete(String uuid) {

        if (!isExists(uuid)) {
            throw new NotExistStorageException(uuid);
        } else {
            DeleteElement(uuid);
        }
    }

    public Resume get(String uuid) {
        if (!isExists(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        return getElement(uuid);
    }

    protected abstract Resume getElement(String uuid);


    protected abstract void updateElement(Resume r);

    protected abstract void DeleteElement(String uuid);

    protected abstract void insertElement(Resume r);

    protected abstract boolean isExists(String uuid);


}
