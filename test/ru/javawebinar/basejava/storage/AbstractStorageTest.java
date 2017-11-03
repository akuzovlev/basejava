package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    private static final ListSection ACHIEVEMENT = new ListSection(new ArrayList<String>(Arrays.asList("Достиг того", "Достиг сего", "Достиг этого самого")));
    private static final ListSection QUALIFICATIONS = new ListSection(new ArrayList<String>(Arrays.asList("Технологии 1", "Технологии 2", "Технологии 3")));
    private static final OnePeriod ONE_PERIOD_1 =
            new OnePeriod(LocalDate.of(2016, 1, 1), LocalDate.of(2017, 2, 2), "Айтишник", "Какое то описание");

    private static final OnePeriod ONE_PERIOD_2 =
            new OnePeriod(LocalDate.of(2015, 3, 3), LocalDate.of(2016, 4, 4), "junior Айтишник", "Работа, работа");

    private static final  ArrayList<OnePeriod> PERIOD_LIST_1 = new ArrayList<>(Arrays.asList(ONE_PERIOD_1));
    private static final  ArrayList<OnePeriod> PERIOD_LIST_2 = new ArrayList<>(Arrays.asList(ONE_PERIOD_2));
    private static final  Organization ORGANIZATION_1 = new Organization(new Link("111", "ccskrf"),PERIOD_LIST_1);
    private static final  Organization ORGANIZATION_2 = new Organization(new Link("222", "ffg"),PERIOD_LIST_2);



    private static final OnePeriod ONE_PERIOD_3 = new OnePeriod(LocalDate.of(2012, 1, 1), LocalDate.of(2013, 2, 2), "Аспирант", "");
    private static final OnePeriod ONE_PERIOD_4 = new OnePeriod(LocalDate.of(2010, 3, 3), LocalDate.of(2011, 4, 4), "студент", "");
    private static final  ArrayList<OnePeriod> PERIOD_LIST_3 = new ArrayList<>(Arrays.asList(ONE_PERIOD_3));
    private static final  ArrayList<OnePeriod> PERIOD_LIST_4 = new ArrayList<>(Arrays.asList(ONE_PERIOD_4));
    private static final  Organization EDUCATION_1 = new Organization(new Link("111", ""),PERIOD_LIST_3);
    private static final  Organization EDUCATION_2 = new Organization(new Link("222", "hjhg"),PERIOD_LIST_4);

    static {

        RESUME_1 = new Resume(UUID_1, "Name1");
        RESUME_1.addContact(ContactType.PHONE, "+7 (495) 111 11 11");
        RESUME_1.addContact(ContactType.SKYPE, "skypelink");
        RESUME_1.addSection(SectionType.PERSONAL, new TextSection("качество 1, качество 2, качество 3"));
        RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("Самый главный айтишник"));
        RESUME_1.addSection(SectionType.ACHIEVEMENT, ACHIEVEMENT);
        RESUME_1.addSection(SectionType.QUALIFICATIONS, QUALIFICATIONS);
        RESUME_1.addSection(SectionType.EXPERIENCE, new OrganizationSection(new ArrayList<Organization>(Arrays.asList(ORGANIZATION_1,ORGANIZATION_2))));
        RESUME_1.addSection(SectionType.EDUCATION, new OrganizationSection(new ArrayList<Organization>(Arrays.asList(EDUCATION_1,EDUCATION_2))));

        RESUME_2 = new Resume(UUID_2, "Name2");
        RESUME_3 = new Resume(UUID_3, "Name3");
        RESUME_4 = new Resume(UUID_4, "Name4");
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        Resume newResume = new Resume(UUID_1, "New Name");
        storage.update(newResume);
        assertTrue(newResume == storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(list, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}