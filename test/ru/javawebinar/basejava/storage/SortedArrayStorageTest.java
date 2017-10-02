package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Created by KuzovleA on 02.10.2017.
 */
public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

     @Override
    protected Resume[] getProperResumeArray(Resume[] r) {
        Arrays.sort(r);
        return r;
    }
}
