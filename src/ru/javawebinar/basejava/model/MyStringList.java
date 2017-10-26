package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class MyStringList implements DataInterface {

    private List<String> data = new ArrayList<>();

    @Override
    public void addData(List<String> dataList) {
        data.addAll(dataList);
    }

    @Override
    public List<String> getData() {
        return new ArrayList<String>(data);
    }

    @Override
    public void editData(List<String> dataList) {

    }
}
