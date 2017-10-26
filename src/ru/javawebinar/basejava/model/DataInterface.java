package ru.javawebinar.basejava.model;

import java.util.List;


public interface DataInterface {

    public void addData(List<String> dataList);

    public List<String> getData();

    public void editData(List<String> dataList);

}
