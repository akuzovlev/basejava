package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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

                List<String> data = entry.getValue().getDataAsStringList();
                dos.writeInt(data.size());
                for (String s : data) {
                    if (s == null) {
                        dos.writeUTF("null");
                    } else {
                        dos.writeUTF(s);
                    }
                }
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
            SectionType st = SectionType.valueOf(dis.readUTF());
            size = dis.readInt();
            resume.addSection(st, new TextSection(dis.readUTF()));

            st = SectionType.valueOf(dis.readUTF());
            size = dis.readInt();
            resume.addSection(st, new TextSection(dis.readUTF()));

            st = SectionType.valueOf(dis.readUTF());
            List<String> items = new ArrayList<>();
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                items.add(dis.readUTF());
            }
            resume.addSection(st, new ListSection(items));

            st = SectionType.valueOf(dis.readUTF());
            items = new ArrayList<>();
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                items.add(dis.readUTF());
            }
            resume.addSection(st, new ListSection(items));

            st = SectionType.valueOf(dis.readUTF());
            size = dis.readInt();
            size = Integer.parseInt(dis.readUTF());
            List<Organization> organizations = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Link link = new Link(dis.readUTF(), dis.readUTF());
                int positionsSize = Integer.parseInt(dis.readUTF());
                List<Organization.Position> positions = new ArrayList<>();
                for (int j = 0; j < positionsSize; j++) {
                    positions.add(new Organization.Position(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
                }
                organizations.add(new Organization(link, positions));
            }
            resume.addSection(st, new OrganizationSection(organizations));


            st = SectionType.valueOf(dis.readUTF());
            size = dis.readInt();
            size = Integer.parseInt(dis.readUTF());
            organizations = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Link link = new Link(dis.readUTF(), dis.readUTF());
                int positionsSize = Integer.parseInt(dis.readUTF());
                List<Organization.Position> positions = new ArrayList<>();
                for (int j = 0; j < positionsSize; j++) {
                    positions.add(new Organization.Position(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
                }
                organizations.add(new Organization(link, positions));
            }
            resume.addSection(st, new OrganizationSection(organizations));

            return resume;
        }
    }
}