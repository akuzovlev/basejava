package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * ru.javawebinar.basejava.model.Resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private String fullName;
    private List<Contact> contacts = new ArrayList<>();
    private String personal;
    private String position;
    private List<String> achievements = new ArrayList<>();
    private List<String> qualifications = new ArrayList<>();
    private List<Experience> workExpeirence = new ArrayList<>();
    private List<Experience> education = new ArrayList<>();

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



    public List<Contact> getContacts() {
        return contacts;
    }

    public String getPersonal() {
        return personal;
    }

    public String getPosition() {
        return position;
    }

    public List<String> getAchievements() {
        return achievements;
    }

    public List<String> getQualifications() {
        return qualifications;
    }

    public List<Experience> getWorkExpeirence() {
        return workExpeirence;
    }

    public List<Experience> getEducation() {
        return education;
    }


    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void addAchievement(String achievement) {
        achievements.add(achievement);
    }

    public void addQualifications(String qualification) {
        qualifications.add(qualification);
    }

    public void addWorkExpeirence(Experience workExpeirence) {
        this.workExpeirence.add(workExpeirence);
    }

    public void addEducation(Experience education) {
        this.education.add(education);
    }
}