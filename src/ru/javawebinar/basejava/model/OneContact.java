package ru.javawebinar.basejava.model;


import java.util.ArrayList;
import java.util.List;

class OneContact {

    private String contactInfo;
    private String hyperlink;


    public void addData(String contact, String link) {
        contactInfo = contact;
        hyperlink = link;
    }


    public List<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        list.add(contactInfo);
        list.add(hyperlink);
        return list;
    }

    public void editData(String contact, String link) {

    }
}
