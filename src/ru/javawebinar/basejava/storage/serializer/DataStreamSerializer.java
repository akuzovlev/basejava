package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                entry.getValue().write(dos);
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            if (size == 0) {
                return resume;
            }
            for (int i = 0; i < size; i++) {
                addSection(resume, dis);
            }
            return resume;
        }
    }

    private void addSection(Resume r, DataInputStream dis) throws IOException {
        SectionType st = SectionType.valueOf(dis.readUTF());
        String sectionClass = dis.readUTF();
        switch (sectionClass) {
            case "TextSection":
                addTextSection(r, dis, st);
                break;
            case "ListSection":
                addListSection(r, dis, st);
                break;
            case "OrganizationSection":
                addOrganizationSection(r, dis, st);
                break;
            default:
                break;
        }
    }


    private void addTextSection(Resume r, DataInputStream dis, SectionType st) throws IOException {
        r.addSection(st, new TextSection(dis.readUTF()));
    }

    private void addListSection(Resume r, DataInputStream dis, SectionType st) throws IOException {
        List<String> items = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            items.add(dis.readUTF());
        }
        r.addSection(st, new ListSection(items));
    }

    private void addOrganizationSection(Resume r, DataInputStream dis, SectionType st) throws IOException {
        List<Organization> organizations = new ArrayList<>();
        int size = dis.readInt();
        Link link;
        for (int i = 0; i < size; i++) {
            if (dis.readBoolean()) {
                link = new Link(dis.readUTF(), dis.readUTF());
            } else {
                link = new Link(dis.readUTF(), null);
            }
            int posSize = dis.readInt();
            List<Organization.Position> positions = new ArrayList<>();
            for (int j = 0; j < posSize; j++) {
                if (dis.readBoolean()) {
                    positions.add(new Organization.Position(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
                } else {
                    positions.add(new Organization.Position(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), null));
                }
            }
            organizations.add(new Organization(link, positions));
        }
        r.addSection(st, new OrganizationSection(organizations));
    }

}