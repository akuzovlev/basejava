package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.basejava.util.DateUtil.NOW;

public class ResumeServlet extends HttpServlet {

    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType stype : SectionType.values()) {
            String[] value = request.getParameterValues(stype.name());
            if (value != null && value[0].trim().length() != 0) {
                switch (stype) {
                    case PERSONAL:
                    case OBJECTIVE:
                        r.addSection(stype, new TextSection(value[0]));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.addSection(stype, new ListSection(value));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = new ArrayList<>();
                        for (int i = 0; i < value.length; ) {
                            String[] positionData = request.getParameterValues(value[i]);
                            if (positionData == null) {
                                organizations.add(new Organization(value[i], value[i + 1]));
                                i += 2;
                            } else {
                                List<Organization.Position> positions = new ArrayList<>();
                                for (int j = 0; j < positionData.length; ) {
                                    if (!positionData[j].isEmpty()) {
                                        positions.add(new Organization.Position(LocalDate.parse(positionData[j]), !positionData[j + 1].isEmpty() ? LocalDate.parse(positionData[j + 1]) : NOW, positionData[j + 2], positionData[j + 3]));

                                    }
                                    j += 4;
                                }
                                organizations.add(new Organization(new Link(value[i], value[i + 1]), positions));
                                i += 2;
                            }
                        }
                        r.addSection(stype, new OrganizationSection(organizations));

                        break;
                }
            } else {
                r.getSections().remove(stype);
            }
        }


        storage.update(r);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            case "add":
                r = new Resume("");
                storage.save(r);
                action = "edit";
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}