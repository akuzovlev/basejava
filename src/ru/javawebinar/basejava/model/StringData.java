package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;


public class StringData implements DataInterface {

    private String field;

    @Override
    public void addData(List<String> dataList) {
        field = dataList.get(0);
    }

    @Override
    public List<String> getData() {
        ArrayList<String> rez = new ArrayList<String>();
        rez.add(0, field);
        return rez;
    }

    @Override
    public void editData(List<String> dataList) {

    }
}


