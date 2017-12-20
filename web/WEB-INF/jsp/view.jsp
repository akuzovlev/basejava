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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>

    <p>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <jsp:useBean id="sectionType"
                         type="ru.javawebinar.basejava.model.SectionType"/>
    <h3>${sectionType.title}</h3>
    <p>
            <c:set var="section" value="<%=resume.getSection(sectionType)%>"/>
            <jsp:useBean id="section"
                         type="ru.javawebinar.basejava.model.Section"/>
        <c:choose>
            <c:when test="${sectionType.title == 'Личные качества' || sectionType.title == 'Позиция'}" >${resume.getSection(sectionType)}</c:when>
        <c:when test="${sectionType.title == 'Достижения' || sectionType.title == 'Квалификация'}">
        <c:forEach var="record" items="<%=((ListSection)section).getItems()%>">
                &#9672; ${record} <br>
        </c:forEach>
        </c:when>
        <c:when test="${sectionType.title == 'Опыт работы' || sectionType.title == 'Образование'}">
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
         </c:when>
        <c:otherwise>$</c:otherwise>
        </c:choose>
        <br>
        </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>