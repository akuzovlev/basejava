package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

/**
 * Created by KuzovleA on 06.10.2017.
 */
public abstract class AbstractStorage implements Storage {

    public abstract int size();

    public abstract void clear();

    public void update(Resume r) {
        updateElement(r, checkExistAndReturnIndex(r.getUuid()));
    }

    public abstract Resume[] getAll();

    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            insertElement(r, index);
        }
    }

    public void delete(String uuid) {
        deleteElement(uuid, checkExistAndReturnIndex(uuid));
    }

    public Resume get(String uuid) {
        return getElement(uuid, checkExistAndReturnIndex(uuid));
    }

    protected abstract Resume getElement(String uuid, int index);


    protected abstract void updateElement(Resume r, int index);

    protected abstract void deleteElement(String uuid, int index);

    protected abstract void insertElement(Resume r, int index);

    protected abstract int getIndex(String uuid);

    protected int checkExistAndReturnIndex(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }


}
