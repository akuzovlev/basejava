package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KuzovleA on 06.10.2017.
 */
public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> resumeMap = new HashMap<String, Resume>();


    @Override
    public int size() {
        return resumeMap.size();
    }

    @Override
    public void clear() {
        resumeMap.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] arr = new Resume[resumeMap.size()];
        Arrays.sort(resumeMap.values().toArray(arr));
        return arr;
    }

    @Override
    public Resume getElement(Object key) {
        return resumeMap.get((String) key);
    }

    @Override
    protected void updateElement(Resume r, Object key) {
        resumeMap.put(r.getUuid(), r);
    }

    @Override
    protected void deleteElement(Object key) {
        resumeMap.remove((String) key);
    }


    @Override
    protected void insertElement(Resume r, int index) {
        resumeMap.put(r.getUuid(), r);
    }

    @Override
    protected Object getIndex(String uuid) {
        return (resumeMap.get(uuid) != null) ? uuid : -1;
    }


}
