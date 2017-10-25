package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;


public enum Contacts {

    PHONE("Тел.:", new OneContact()),
    SKYPE("Skype:", new OneContact()),
    EMAIL("Почта:", new OneContact()),
    LINKEDIN("LinkedIn", new OneContact()),
    GITHUB("GitHub", new OneContact()),
    STACKOWERFLOW("Stackoverflow", new OneContact()),
    WEBSITE("Website", new OneContact());

    private String type;
    private OneContact contact;

    Contacts(String type, OneContact contact) {
        this.type = type;
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public OneContact getContact() {
        return contact;
    }
}
