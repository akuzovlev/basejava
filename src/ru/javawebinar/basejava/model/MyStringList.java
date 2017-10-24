package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.storage.ListStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KuzovleA on 24.10.2017.
 */
public class MyStringList implements DataInterface {

    List<String> field = new ArrayList<>();

    @Override
    public void addData(Object o) {

    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public void editData() {

    }
}
