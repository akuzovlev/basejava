package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.UUID;


/**
 * ru.javawebinar.basejava.model.Resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private String fullName;

    private DataInterface personal = new StringData();
    private DataInterface objective = new StringData();
    private DataInterface achievement = new MyStringList();
    private DataInterface qualification = new MyStringList();
    private DataInterface expierence = new Experience();
    private DataInterface education = new Experience();

    private Contacts contacts = new Contacts();


    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode() + fullName.hashCode();
    }

    @Override
    public String toString() {
        return uuid + " " + fullName;
    }

    @Override
    public int compareTo(Resume o) {
        return this.fullName.compareTo(o.getFullName());
    }

    //------------------------------------------------------------------------


    public DataInterface getFieldBySection(SectionType t) {
        switch (t) {
            case PERSONAL:
                return personal;
            case OBJECTIVE:
                return objective;
            case ACHIEVEMENT:
                return achievement;
            case QUALIFICATIONS:
                return qualification;
            case EXPERIENCE:
                return expierence;
            case EDUCATION:
                return education;
            default:
                throw new RuntimeException("SectionType Error");

        }
    }

    public void addData(List<String> data, SectionType t) {
        getFieldBySection(t).addData(data);
    }

    public List<String> getData(SectionType t) {
        return getFieldBySection(t).getData();
    }

    public void addContact(String contact, String link, ContactsFields f) {
        contacts.addData(contact, link, f);
    }

    public List<String> getContact(ContactsFields f) {
        return contacts.getContact(f);
    }

}