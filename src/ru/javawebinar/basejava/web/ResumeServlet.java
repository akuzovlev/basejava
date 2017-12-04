package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionType;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        StringBuilder sb = new StringBuilder();
        sb.append("<table border=\"1\">");

        sb.append("<tr>");

        sb.append("<td>");
        sb.append("Uuid");
        sb.append("</td>");

        sb.append("<td>");
        sb.append("Fullname");
        sb.append("</td>");

        sb.append("<td>");
        sb.append("Contacts");
        sb.append("</td>");

        for (SectionType s : SectionType.values()) {
            sb.append("<td>");
            sb.append(s.getTitle());
            sb.append("</td>");
        }

        sb.append("</tr>");

        for (Resume r : Config.get().getStorage().getAllSorted()) {
            sb.append("<tr>");

            sb.append("<td>");
            sb.append(r.getUuid());
            sb.append("</td>");

            sb.append("<td>");
            sb.append(r.getFullName());
            sb.append("</td>");

            sb.append("<td>");
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                sb.append(e.getKey() + ": " + e.getValue() + "<br>");
            }
            sb.append("</td>");

            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                sb.append("<td>");
                sb.append(e.getValue());
                sb.append("</td>");
            }

            sb.append("</tr>");
        }

        sb.append("</table>");
        OutputStream outStream = response.getOutputStream();
        outStream.write(sb.toString().getBytes("UTF-8"));
        outStream.flush();
        outStream.close();
    }
}