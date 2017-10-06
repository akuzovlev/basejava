package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by KuzovleA on 06.10.2017.
 */
public class MapStorage extends AbstractStorage {

    private final static Map<String, Resume> resumeMap = new LinkedHashMap<String, Resume>();


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
        return resumeMap.values().toArray(arr);
    }

    @Override
    public Resume getElement(String uuid, int index) {
        return resumeMap.get(uuid);
    }

    @Override
    protected void updateElement(Resume r, int index) {
        resumeMap.put(r.getUuid(), r);
    }

    @Override
    protected void DeleteElement(String uuid, int index) {
        resumeMap.remove(uuid);
    }


    @Override
    protected void insertElement(Resume r, int index) {
        resumeMap.put(r.getUuid(), r);
    }

    @Override
    protected int getIndex(String uuid) {
        return (resumeMap.get(uuid) != null) ? 1 : -1;
    }

}
