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
                writeSection(entry, dos);
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

    private void writeSection(Map.Entry<SectionType, Section> entry, DataOutputStream dos) throws IOException {
        SectionType sectionType = entry.getKey();
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                writeTextSection(dos, (TextSection) entry.getValue());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeListSection(dos, (ListSection) entry.getValue());
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeOrganizationSection(dos, (OrganizationSection) entry.getValue());
                break;
        }
    }

    private void writeTextSection(DataOutputStream dos, TextSection textSection) throws IOException {
        dos.writeUTF("TextSection");
        dos.writeUTF(textSection.getContent());
    }

    private void writeListSection(DataOutputStream dos, ListSection listSection) throws IOException {
        dos.writeUTF("ListSection");
        List<String> items = listSection.getItems();
        dos.writeInt(items.size());
        for (String s : items) {
            dos.writeUTF(s);
        }
    }

    private void writeOrganizationSection(DataOutputStream dos, OrganizationSection organizationSection) throws IOException {
        dos.writeUTF("OrganizationSection");
        List<Organization> organizations = organizationSection.getOrganizations();
        dos.writeInt(organizations.size());
        for (Organization org : organizations) {
            dos.writeUTF(org.getHomePage().getName());
            String url = org.getHomePage().getUrl();
            dos.writeUTF((url != null) ? url : "");
            List<Organization.Position> positions = org.getPositions();
            dos.writeInt(positions.size());
            for (Organization.Position position : positions) {
                dos.writeUTF(position.getStartDate().toString());
                dos.writeUTF(position.getEndDate().toString());
                dos.writeUTF(position.getTitle());
                String description = position.getDescription();
                dos.writeUTF((description != null) ? description : "");
            }
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

            String name = dis.readUTF();
            String url = dis.readUTF();
            if (url.equals("")) {
                link = new Link(name, null);
            } else {
                link = new Link(name, url);
            }

            int posSize = dis.readInt();
            List<Organization.Position> positions = new ArrayList<>();
            for (int j = 0; j < posSize; j++) {
                LocalDate startDate = LocalDate.parse(dis.readUTF());
                LocalDate endDate = LocalDate.parse(dis.readUTF());
                String title = dis.readUTF();
                String description = dis.readUTF();
                if (description.equals("")) description = null;
                positions.add(new Organization.Position(startDate, endDate, title, description));
            }
            organizations.add(new Organization(link, positions));
        }
        r.addSection(st, new OrganizationSection(organizations));
    }
}