package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

/**
 * Created by kuzovlea on 27.09.2017.
 */

public class SortedArrayStorage extends AbstractArrayStorage{

    @Override
    public void save(Resume r) {

    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }


}
