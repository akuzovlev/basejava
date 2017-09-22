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
        lastElement++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < lastElement; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < lastElement; i++) {
            if (storage[i].uuid.equals(uuid)) {
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
    Resume[] getAll() {
        return Arrays.copyOf(storage, lastElement);
    }

    int size() {
        return lastElement;
    }
}
