package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";


    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_3));
        storage.save(new Resume(UUID_2));
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume res = new Resume(UUID_1);
        storage.update(res);
        Assert.assertEquals(res, storage.get(UUID_1));
    }

    @Test
    public void getAll() throws Exception {
        Resume[] r = {new Resume(UUID_1),new Resume(UUID_3),new Resume(UUID_2)};
        Assert.assertArrayEquals(getProperResumeArray(r), storage.getAll());

    }

    @Test
    public void save() throws Exception {
        storage.save(new Resume("1"));
        storage.get("1");
        Assert.assertEquals(4, storage.size());
        Assert.assertArrayEquals(getProperResumeArray(storage.getAll()), storage.getAll());

    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        storage.get(UUID_1);
        Assert.assertArrayEquals(getProperResumeArray(storage.getAll()), storage.getAll());
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void getExist() throws Exception {
        storage.save(new Resume(UUID_1));
    }

    protected abstract Resume [] getProperResumeArray (Resume[] r);

}