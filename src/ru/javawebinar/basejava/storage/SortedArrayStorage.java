package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Created by kuzovlea on 27.09.2017.
 */

public class SortedArrayStorage extends AbstractArrayStorage {


    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void saveHelper(Resume r, int index) {
        int insPoint = Math.abs(index) - 1;
        System.arraycopy(storage, insPoint, storage, insPoint + 1, size - insPoint);
        storage[insPoint] = r;
        size++;
    }


    @Override
    protected void delHelper(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }
}
