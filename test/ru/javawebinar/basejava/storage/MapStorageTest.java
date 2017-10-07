package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;

/**
 * Created by Storm on 06.10.2017.
 */
public class MapStorageTest extends AbstractArrayStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    public void saveOverflow() throws Exception {
        throw new StorageException("", "");
    }

}
