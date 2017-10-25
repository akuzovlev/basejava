package ru.javawebinar.basejava.model;

/**
 * Created by KuzovleA on 24.10.2017.
 */
public class StringData implements DataInterface {

    String field;

    @Override
    public void addData(Object o) {
        field += (String) o;
    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public void editData() {

    }
}
