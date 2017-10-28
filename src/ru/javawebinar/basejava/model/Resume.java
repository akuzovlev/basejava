package ru.javawebinar.basejava.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * ru.javawebinar.basejava.model.Resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private String fullName;
    private Map<SectionType,DataInterface> sections = new HashMap<>();
    private Map<ContactType,Contact> contacts = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
        sections.put(SectionType.PERSONAL, new StringData());
        sections.put(SectionType.OBJECTIVE, new StringData());
        sections.put(SectionType.ACHIEVEMENT, new MyStringList());
        sections.put(SectionType.QUALIFICATIONS, new MyStringList());
        sections.put(SectionType.EXPERIENCE, new Experience());
        sections.put(SectionType.EDUCATION, new Experience());
        for (ContactType t : ContactType.values()) {
            contacts.put(t,new Contact());
        }
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


    public void addDataToSection(List<String> data, SectionType t) {
        sections.get(t).addData(data);
    }

    public List<String> getDataFromSection(SectionType t) {
        return sections.get(t).getData();
    }

    public void addContact(String contact, String link, ContactType t) {
        contacts.get(t).addData(contact,link);
    }

    public List<String> getContact(ContactType f) {
        return contacts.get(f).getData();
    }

}