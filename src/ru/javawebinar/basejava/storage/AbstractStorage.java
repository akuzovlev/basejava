package ru.javawebinar.basejava.storage;

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

    public abstract void save(Resume r);

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

    protected abstract Object checkExistAndReturnIndex(String uuid);


}
