package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KuzovleA on 24.10.2017.
 */
public class Experience implements DataInterface {

    List<OnePlaceExperience> field = new ArrayList<>();

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