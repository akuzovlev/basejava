package webapp.storage;

import webapp.model.Resume;

/**
 * Created by kuzovlea on 27.09.2017.
 */
public interface Storage {

    void clear();

    void update(Resume r);

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    Resume[] getAll();

    int size();
}