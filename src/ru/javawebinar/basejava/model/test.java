package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {

    public static void main(String[] args) {


        Resume r = new Resume("11", "test");

        List<String> list = new ArrayList<>(Arrays.asList("test1", "test2", "test3", "test4", "test5"));


        for (ContactsFields f : ContactsFields.values()) {
            r.addContact("contact1", "href1", f);
        }

        for (SectionType t : SectionType.values()) {

            r.addDataToSection(list, t);

        }

        printAllData(r);
        System.out.println("-------------------");

        Resume r2 = new Resume("22", "222test222");

        list = new ArrayList<>(Arrays.asList("22test1", "22test2", "22test3", "22test4", "22test5"));

        for (ContactsFields f : ContactsFields.values()) {
            r2.addContact("2222contact1", "22222href1", f);
        }

        for (SectionType t : SectionType.values()) {

            r2.addDataToSection(list, t);

        }

        printAllData(r2);
        System.out.println("-------------------");
        printAllData(r);


    }

    public static void printAllData(Resume r) {
        System.out.println("Fullname: " + r.getFullName());

        for (ContactsFields f : ContactsFields.values()) {
            System.out.println(f.getTitle() + "  " + r.getContact(f));
        }


        for (SectionType t : SectionType.values()) {
            System.out.println(t.getTitle() + "  " + r.getDataFromSection(t));
        }
    }
}
