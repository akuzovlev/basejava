package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by KuzovleA on 06.10.2017.
 */
public class ListStorage extends AbstractStorage {


    private final List<Resume> resumeList = new ArrayList<Resume>();


    @Override
    public int size() {
        return resumeList.size();
    }

    @Override
    public void clear() {
        resumeList.clear();

    }

    @Override
    public Resume[] getAll() {
        Resume[] arr = new Resume[resumeList.size()];

        return resumeList.toArray(arr);
    }

    @Override
    public Resume getElement(Object key) {
        return resumeList.get((int) key);
    }

    @Override
    protected void updateElement(Resume r, Object key) {
        resumeList.set((int) key, r);
    }

    @Override
    protected void deleteElement(Object key) {
        resumeList.remove((int) key);
    }

    @Override
    protected void insertElement(Resume r, int index) {
        resumeList.add(r);
    }

    @Override
    protected Object getIndex(String uuid) {

        for (Resume r : resumeList) {
            if (r.getUuid().equals(uuid)) {
                return resumeList.indexOf(r);
            }
        }
        return -1;
    }


}
