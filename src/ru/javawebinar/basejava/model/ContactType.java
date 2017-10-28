package ru.javawebinar.basejava.model;

public enum ContactType {

    PHONE("Тел.:"),
    SKYPE("Skype:"),
    EMAIL("Почта:"),
    LINKEDIN("LinkedIn"),
    GITHUB("GitHub"),
    STACKOWERFLOW("Stackoverflow"),
    WEBSITE("Website");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
