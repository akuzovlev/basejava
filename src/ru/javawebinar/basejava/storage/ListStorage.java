package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by KuzovleA on 06.10.2017.
 */
public class ListStorage extends AbstractStorage {


    private final static List<Resume> resumeList = new ArrayList<Resume>();


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
    public Resume getElement(String uuid, int index) {
        return resumeList.get(resumeList.indexOf(new Resume(uuid)));
    }

    @Override
    protected void updateElement(Resume r, int index) {
        resumeList.set(resumeList.indexOf(r), r);
    }

    @Override
    protected void DeleteElement(String uuid, int index) {
        resumeList.remove(new Resume(uuid));
    }

    @Override
    protected void insertElement(Resume r, int index) {
        resumeList.add(r);
    }

    @Override
    protected int getIndex(String uuid) {

        return resumeList.indexOf(new Resume(uuid));
    }


}
