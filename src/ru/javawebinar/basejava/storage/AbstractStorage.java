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
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            updateElement(r, index);
        }
    }

    public abstract Resume[] getAll();

    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else if (size() >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            insertElement(r, index);
        }
    }

    public void delete(String uuid) {

        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            DeleteElement(uuid, index);
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getElement(uuid, index);
    }

    protected abstract Resume getElement(String uuid, int index);


    protected abstract void updateElement(Resume r, int index);

    protected abstract void DeleteElement(String uuid, int index);

    protected abstract void insertElement(Resume r, int index);

    protected abstract int getIndex(String uuid);


}
