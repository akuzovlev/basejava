package ru.javawebinar.basejava.model;

public enum ContactsFields {

    PHONE("Тел.:"),
    SKYPE("Skype:"),
    EMAIL("Почта:"),
    LINKEDIN("LinkedIn"),
    GITHUB("GitHub"),
    STACKOWERFLOW("Stackoverflow"),
    WEBSITE("Website");

    private String title;

    ContactsFields(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
