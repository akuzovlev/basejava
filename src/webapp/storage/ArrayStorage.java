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
        //TODO реализовать
        //TODO проверка что существует  System.out.println("Resume ...") (бул существование в отдельный метод)
    }

    public void save(Resume r) {
        //TODO проверка что не существует
        //TODO проверку на переполнениe: System.out.println("...")
        storage[lastElement] = r;
        lastElement++;
    }

    public Resume get(String uuid) {
        //TODO проверка что существует
        for (int i = 0; i < lastElement; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        //TODO проверка что существует
        for (int i = 0; i < lastElement; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[lastElement - 1];
                storage[lastElement - 1] = null;
                lastElement--;
                return;
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
}
