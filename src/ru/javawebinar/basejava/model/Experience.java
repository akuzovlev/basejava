package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class Experience implements DataInterface {

    private List<OnePlaceExperience> data = new ArrayList<>();

    @Override
    public void addData(List<String> dataList) {
        OnePlaceExperience place = new OnePlaceExperience();
        place.setCompany(dataList.get(0));
        place.setDatesFromTo(dataList.get(1));
        place.setPosition(dataList.get(2));
        place.setDescription(dataList.get(3));
        data.add(place);
    }

    @Override
    public List<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        for (OnePlaceExperience exp : data) {
            list.add(exp.getCompany());
            list.add(exp.getDatesFromTo());
            list.add(exp.getPosition());
            list.add(exp.getDescription());
        }
        return list;
    }

    @Override
    public void editData(List<String> dataList) {
    }
}
