import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int lastElement = 0;

    void clear() {
        Arrays.fill(storage, null);
        lastElement = 0;
    }

    void save(Resume r) {
        storage[lastElement] = r;
        lastElement ++;
    }

    Resume get(String uuid) {
        for (Resume res : storage) {
            if (res.toString().equals(uuid)) {
                return res;
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < lastElement; i++) {
            if (storage[i].toString().equals(uuid)) {
                if (lastElement == 1) {
                    storage[i] = null;
                    lastElement = 0;
                    return;
                } else {
                    storage[i] = storage[lastElement - 1];
                    storage[lastElement - 1] = null;
                    lastElement --;
                    return;
                }
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage,lastElement);
    }

    int size() {
        return lastElement;
    }
}
