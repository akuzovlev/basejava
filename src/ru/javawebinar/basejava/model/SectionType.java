package ru.javawebinar.basejava.model;

public enum SectionType {
    PERSONAL("Личные качества", new StringData()),
    OBJECTIVE("Позиция", new StringData()),
    ACHIEVEMENT("Достижения", new MyStringList()),
    QUALIFICATIONS("Квалификация", new MyStringList()),
    EXPERIENCE("Опыт работы", new Experience()),
    EDUCATION("Образование", new Experience());

    private String title;
    private DataInterface field;

    SectionType(String title, DataInterface field) {
        this.title = title;
        this.field = field;
    }

    public String getTitle() {
        return title;
    }

    public DataInterface getField() {
        return field;
    }



}