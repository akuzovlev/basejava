<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="contactType" items="<%=ContactType.values()%>">
            <dl>
                <dt>${contactType.title}</dt>
                <dd><input type="text" name="${contactType.name()}" size=30 value="${resume.getContact(contactType)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
        <dl>



                <jsp:useBean id="sectionType"
                             type="ru.javawebinar.basejava.model.SectionType"/>
                <dt>${sectionType.title}</dt>
                <p>
                <c:set var="section" value="<%=resume.getSection(sectionType)%>"/>
                <jsp:useBean id="section"
                             type="ru.javawebinar.basejava.model.Section"/>
                <c:choose>
                    <c:when test="${sectionType.title == 'Личные качества'}">
                <dd><textarea cols="100" rows="5" title="" name="${sectionType.name()}">${resume.getSection(sectionType)}</textarea></dd>
                    </c:when>

                    <c:when test="${sectionType.title == 'Позиция'}">
                <dd><textarea cols="100" rows="5" title="" name="${sectionType.name()}">${resume.getSection(sectionType)}</textarea></dd>
                </c:when>
                    <c:when test="${sectionType.title == 'Достижения'}">
                <dd><textarea cols="100" rows="5" title="" name="${sectionType.name()}"><c:forEach var="record" items="<%=((ListSection)section).getItems()%>">${record}
</c:forEach></textarea></dd>
                    </c:when>
                    <c:when test="${sectionType.title == 'Квалификация'}">
                <dd><textarea cols="100" rows="5" title="" name="${sectionType.name()}"><c:forEach var="record" items="<%=((ListSection)section).getItems()%>">${record}
</c:forEach></textarea></dd>
                    </c:when>

                    <c:when test="${sectionType.title == 'Опыт работы'}">
            <dd><textarea cols="100" rows="5" title="" name="${sectionType.name()}">
                        <c:forEach var="record" items="<%=((OrganizationSection)section).getOrganizations()%>">
                            <a href='${record.homePage.url}'>${record.homePage.name}</a>
                            <br>
                            <c:forEach var="position" items="${record.positions}">
                                from ${position.startDate} to ${position.endDate}   <b>${position.title}</b> <br>
                                ${position.description}
                                <br>
                            </c:forEach>
                            <br>
                        </c:forEach>
                             </textarea></dd>
                    </c:when>
                    <c:when test="${sectionType.title == 'Образование'}">
                        <dd><textarea cols="100" rows="5" title="" name="${sectionType.name()}">
                        <c:forEach var="record" items="<%=((OrganizationSection)section).getOrganizations()%>">
                            <a href='${record.homePage.url}'>${record.homePage.name}</a>
                            <br>
                            <c:forEach var="position" items="${record.positions}">
                                from ${position.startDate} to ${position.endDate}   <b>${position.title}</b> <br>
                                ${position.description}
                                <br>
                            </c:forEach>
                            <br>
                        </c:forEach>
                        </textarea></dd>
                    </c:when>
                    <c:otherwise>$</c:otherwise>
                </c:choose>
                <br>




          <%--  <dl>
                <dt>${sectionType.title}</dt>
                &lt;%&ndash;<dd><input type="text" name="${sectionType.name()}" size=30 value="${resume.getSection(sectionType)}"></dd>&ndash;%&gt;
                <dd><textarea cols="100" rows="5" title="" name="${sectionType.name()}">${resume.getSection(sectionType)}</textarea></dd>
                --%>


            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>