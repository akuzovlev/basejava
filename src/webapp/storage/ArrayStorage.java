package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int lastElement = 0;

    public void clear() {
        Arrays.fill(storage, null);
        lastElement = 0;
    }

    public void update(Resume r) {
        // проверка что существует  System.out.println("Resume ...")
        int position = isExists(r.getUuid());
        if ( position < 0) {
            System.out.println("Resume is not exists in storage!");
        } else {
            //реализовать
            storage[position] = r;
        }
    }

    public void save(Resume r) {
        // проверка что не существует
        if (isExists(r.getUuid()) >= 0) {
            System.out.println("Resume is already exists in storage!");
        } else {
            //проверку на переполнениe: System.out.println("...")
            if (lastElement == 9999) {
                System.out.println("Resume storage is full!");
            } else {
                storage[lastElement] = r;
                lastElement++;
            }
        }
    }

    public Resume get(String uuid) {
        //проверка что существует
        if (isExists(uuid) < 0) {
            System.out.println("Resume is not exists in storage!");
        } else {
            for (int i = 0; i < lastElement; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    return storage[i];
                }
            }
        }
        return null;
    }

    public void delete(String uuid) {
        // проверка что существует
        if (isExists(uuid) < 0) {
            System.out.println("Resume is not exists in storage!");
        } else {
            for (int i = 0; i < lastElement; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    storage[i] = storage[lastElement - 1];
                    storage[lastElement - 1] = null;
                    lastElement--;
                    return;
                }
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, lastElement);
    }

    public int size() {
        return lastElement;
    }

    private int isExists(String uuid) {
        for (int i = 0; i < lastElement; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
