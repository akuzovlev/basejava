package ru.javawebinar.basejava.model;

import java.util.List;

public class Contacts {

    private OneContact phone = new OneContact();
    private OneContact skype = new OneContact();
    private OneContact email = new OneContact();
    private OneContact linkedin = new OneContact();
    private OneContact github = new OneContact();
    private OneContact stackoverflow = new OneContact();
    private OneContact website = new OneContact();

    public void addData(String contact, String link, ContactsFields f) {
        getFieldBySection(f).addData(contact, link);
    }


    public List<String> getContact(ContactsFields f) {
        return getFieldBySection(f).getData();
    }


    public void editData(List<String> dataList) {

    }


    public OneContact getFieldBySection(ContactsFields t) {
        switch (t) {
            case PHONE:
                return phone;
            case SKYPE:
                return skype;
            case EMAIL:
                return email;
            case LINKEDIN:
                return linkedin;
            case GITHUB:
                return github;
            case STACKOWERFLOW:
                return stackoverflow;
            case WEBSITE:
                return website;
            default:
                throw new RuntimeException("SectionType Error");

        }
    }
}