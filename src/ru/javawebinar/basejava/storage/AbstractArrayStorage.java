package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected void insertElement(Resume r, int index) {
        if (size() >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            InsertArrayElement(r, index);
            size++;
        }
    }

    public void save(Resume r) {
        int index = (int) getIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            insertElement(r, index);
        }
    }

    protected abstract void InsertArrayElement(Resume r, int index);

    protected abstract void fillDeletedElement(int index);

    @Override
    protected Resume getElement(Object key) {
        return storage[(int) key];
    }

    @Override
    protected void updateElement(Resume r, Object key) {
        storage[(int) key] = r;
    }

    @Override
    protected void deleteElement(Object key) {
        fillDeletedElement((int) key);
        storage[size - 1] = null;
        size--;
    }

    protected Object checkExistAndReturnIndex(String uuid) {
        Integer index = (int) getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

}