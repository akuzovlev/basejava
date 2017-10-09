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
        if (getIndex(r.getUuid()) instanceof String) {
            throw new ExistStorageException(r.getUuid());
        }
        int index = (int) getIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            insertElement(r, index);
        }
    }

    public void delete(String uuid) {
        deleteElement(checkExistAndReturnIndex(uuid));
    }

    public Resume get(String uuid) {
        return getElement(checkExistAndReturnIndex(uuid));
    }

    protected abstract Resume getElement(Object key);

    protected abstract void updateElement(Resume r, Object key);

    protected abstract void deleteElement(Object key);

    protected abstract void insertElement(Resume r, int index);

    protected abstract Object getIndex(String uuid);

    protected Object checkExistAndReturnIndex(String uuid) {
        if (getIndex(uuid) instanceof String) {
            return uuid;
        }
        int index = (int) getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }


}
