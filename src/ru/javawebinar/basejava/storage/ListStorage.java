package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;


import java.util.LinkedList;
import java.util.List;

/**
 * Created by KuzovleA on 06.10.2017.
 */
public class ListStorage extends AbstractStorage {


    private final static List<Resume> resumeList = new LinkedList<Resume>();


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
        return (Resume[]) resumeList.toArray();
    }

    @Override
    public Resume get(String uuid) {
        return resumeList.get(resumeList.indexOf(new Resume(uuid)));
    }

    @Override
    protected void DeleteElement(String uuid) {
        resumeList.remove(new Resume(uuid));
    }

    @Override
    protected void insertElement(Resume r) {
        resumeList.add(r);
    }

    @Override
    protected boolean isExists(String uuid) {
        return resumeList.contains(new Resume(uuid));
    }


}
