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
    private static final String UUID_4 = "uuid4";
    private static final Resume r1 = new Resume(UUID_1);
    private static final Resume r2 = new Resume(UUID_2);
    private static final Resume r3 = new Resume(UUID_3);
    private static final Resume r4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(r1);
        storage.save(r3);
        storage.save(r2);
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
        Resume[] r = {r1,r3,r2};
        Assert.assertArrayEquals(getProperResumeArray(r), storage.getAll());

    }

    @Test
    public void save() throws Exception {
        storage.save(r4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(storage.get(UUID_4), r4);
        Resume[] r = {r1,r3,r2,r4};
        Assert.assertArrayEquals(getProperResumeArray(r), storage.getAll());

    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_1);
        Resume[] r = {r2,r3};
        Assert.assertArrayEquals(getProperResumeArray(r), storage.getAll());
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(r1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void getExist() throws Exception {
        storage.save(r1);
    }

    protected abstract Resume [] getProperResumeArray (Resume[] r);

}