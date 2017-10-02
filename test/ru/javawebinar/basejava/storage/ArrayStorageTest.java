package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class ArrayStorageTest extends AbstractArrayStorageTest{

    public ArrayStorageTest() {
        super(new ArrayStorage());
    }

    @Override
    protected Resume[] getProperResumeArray(Resume[] r) {
        return r;
    }
}