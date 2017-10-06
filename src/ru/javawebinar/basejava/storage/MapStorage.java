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
        return (Resume[]) resumeMap.values().toArray();
    }

    @Override
    public Resume get(String uuid) {
        return resumeMap.get(uuid);
    }

    @Override
    protected void DeleteElement(String uuid) {
        resumeMap.remove(uuid);
    }


    @Override
    protected void insertElement(Resume r) {
        resumeMap.put(r.getUuid(), r);
    }

    @Override
    protected boolean isExists(String uuid) {
        return resumeMap.get(uuid) != null;
    }

}