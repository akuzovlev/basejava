package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

/**
 * Created by kuzovlea on 27.09.2017.
 */

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {

        if (getIndex(r.getUuid()) >= 0) {
            System.out.println("Resume " + r.getUuid() + " already exist");
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        } else {

            if (size == 0) {
                storage[size] = r;
                size++;
            } else if (size == 1) {
                if (r.compareTo(storage[size-1]) < 0) {
                    storage[size] = storage[size-1];
                    storage[size-1] = r;
                    size++;
                } else {
                    storage[size] = r;
                    size++;
                }
            } else {
                if (r.compareTo(storage[size - 1]) < 0) {
                    for (int i = size - 2; i >= 0; i--) {
                        if (r.compareTo(storage[i]) > 0 && r.compareTo(storage[i+1]) < 0) {
                            storage[i+2] = storage[i+1];
                            storage[i+1] = r;
                            size++;
                            return;
                        } else {
                            storage[i+2] = storage[i+1];
                            storage[i+1] = r;
                        }
                    }

                } else {
                    storage[size] = r;
                    size++;
                }
            }

        }
    }
        @Override
        public void delete (String uuid){
            int index = getIndex(uuid);
            if (index < 0) {
                System.out.println("Resume " + uuid + " not exist");
            } else {
                for (int i = index; i < size - 1; i++) {
                    storage[i] = storage[i + 1];
                }
                storage[size - 1] = null;
                size--;
            }
        }

        @Override
        protected int getIndex (String uuid){
            Resume searchKey = new Resume();
            searchKey.setUuid(uuid);
            return Arrays.binarySearch(storage, 0, size, searchKey);
        }


    }
