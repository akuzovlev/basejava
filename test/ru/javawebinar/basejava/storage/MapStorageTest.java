package ru.javawebinar.basejava.storage;

import org.junit.Ignore;
import org.junit.Test;
import ru.javawebinar.basejava.exception.StorageException;

/**
 * Created by Storm on 06.10.2017.
 */
public class MapStorageTest extends AbstractArrayStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Ignore
    @Test
    @Override
    public void saveOverflow() throws Exception {

    }

}
