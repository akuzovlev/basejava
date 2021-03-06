<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
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

                <c:choose>
                    <c:when test="${sectionType.title == 'Личные качества' || sectionType.title == 'Позиция'}">
                <dd><textarea cols="100" rows="5" title="" name="${sectionType.name()}">${resume.getSection(sectionType)}</textarea></dd>
                    </c:when>

                    <c:when test="${sectionType.title == 'Достижения'}">

                        <c:set var="ach" value="<%=resume.getSection(sectionType)%>"/>
                        <jsp:useBean id="ach"
                                     class="ru.javawebinar.basejava.model.ListSection"/>

                        <script>
                           function addAch() {
                               $( "#addAch" ).append( "<dd><textarea cols=\"100\" rows=\"5\" title=\"\" name=\"${sectionType.name()}\"></textarea><input type=\"button\" value=\"Удалить поле\" name=\"rmField\" OnClick=\"rmAch(this);\"></dd>" );
                            }
                           function rmAch(element) {
                               $(element).parent().remove()
                           }

                        </script>

                        <c:choose>
                            <c:when test="${ach.items == null}">
                                <dd><textarea cols="100" rows="5" title="" id = "${sectionType.name()}" name="${sectionType.name()}"></textarea>
                                    <input type="button" value="Удалить поле" name="rmField" OnClick="rmAch(this);">
                                </dd>
                            </c:when>
                        </c:choose>

                    <c:forEach var="record" items="<%=ach.getItems()%>">
                        <dd><textarea cols="100" rows="5" title="" id = "${sectionType.name()}" name="${sectionType.name()}">${record}</textarea>
                            <input type="button" value="Удалить поле" name="rmField" OnClick="rmAch(this);">
                        </dd>

                    </c:forEach>
                        <div id="addAch"></div>
                <input type="button" value="Добавить поле" name="addField" OnClick="addAch();">
                    </c:when>

            <c:when test="${sectionType.title == 'Квалификация'}">

                <c:set var="kval" value="<%=resume.getSection(sectionType)%>"/>
                <jsp:useBean id="kval"
                             class="ru.javawebinar.basejava.model.ListSection"/>
                <script>
                    function addKval() {
                        $( "#addKval" ).append( "<dd><textarea cols=\"100\" rows=\"5\" title=\"\" name=\"${sectionType.name()}\"></textarea><input type=\"button\" value=\"Удалить поле\" name=\"rmField\" OnClick=\"rmKval(this);\"></dd>" );

                        function rmKval(element) {
                            $(element).parent().remove()
                        }
                    }
                </script>

                <c:choose>
                    <c:when test="${kval.items == null}">
                        <dd><textarea cols="100" rows="5" title="" id = "${sectionType.name()}" name="${sectionType.name()}"></textarea>
                            <input type="button" value="Удалить поле" name="rmField" OnClick="rmAch(this);">
                        </dd>
                    </c:when>
                </c:choose>

                <c:forEach var="record" items="<%=kval.getItems()%>">
                    <dd><textarea cols="100" rows="5" title="" id = "${sectionType.name()}" name="${sectionType.name()}">${record}</textarea>
                        <input type="button" value="Удалить поле" name="rmField" OnClick="rmAch(this);">
                    </dd>
                </c:forEach>
                <div id="addKval"></div>
                <input type="button" value="Добавить поле" name="addField" OnClick="addKval();">

            </c:when>




                    <c:when test="${sectionType.title == 'Опыт работы' || sectionType.title == 'Образование'}">

                        <c:set var="organizationSection" value="<%=resume.getSection(sectionType)%>"/>
                        <jsp:useBean id="organizationSection"
                                     class="ru.javawebinar.basejava.model.OrganizationSection"/>


            <c:choose>
                <c:when test="${organizationSection.organizations == null}">

                    <dd>Название организации <textarea cols="20" rows="1" title="" name="${sectionType.name()}">${record.homePage.name}</textarea></dd>
                    <dd>Сайт организации <textarea cols="20" rows="1" title="" name="${sectionType.name()}">${record.homePage.url}</textarea></dd>
                    <br>

                        <dd>Дата начала (в формате 2017-01-01)<textarea cols="10" rows="1" title="" name="${record.homePage.name}"></textarea></dd>
                        <dd>Дата окончания (в формате 2017-01-01)<textarea cols="10" rows="1" title="" name="${record.homePage.name}"></textarea></dd>
                    <br><dd>Должность <textarea cols="20" rows="1" title="" name="${record.homePage.name}"></textarea></dd>
                        <dd>Описание <br><textarea cols="100" rows="5" title="" name="${record.homePage.name}"></textarea></dd>

                </c:when>
            </c:choose>


                <c:forEach var="record" items="<%=organizationSection.getOrganizations()%>">

                    <dd>Название организации <textarea cols="20" rows="1" title="" name="${sectionType.name()}">${record.homePage.name}</textarea></dd>
                    <dd>Сайт организации <textarea cols="20" rows="1" title="" name="${sectionType.name()}">${record.homePage.url}</textarea></dd>
                        <br>

                    <c:choose>
                        <c:when test="${record.positions.size() == 0 }">

                                <dd>Дата начала (в формате 2017-01-01)<textarea cols="10" rows="1" title="" name="${record.homePage.name}"></textarea></dd>
                                <dd>Дата окончания (в формате 2017-01-01)<textarea cols="10" rows="1" title="" name="${record.homePage.name}"></textarea></dd>
                            <br><dd>Должность <textarea cols="20" rows="1" title="" name="${record.homePage.name}"></textarea></dd>
                                <dd>Описание <br><textarea cols="100" rows="5" title="" name="${record.homePage.name}"></textarea></dd>
                        </c:when>
                        <c:otherwise>

                    <c:forEach var="position" items="${record.positions}">
                        <dd>Дата начала (в формате 2017-01-01)<textarea cols="10" rows="1" title="" name="${record.homePage.name}">${position.startDate}</textarea></dd>
                        <dd>Дата окончания (в формате 2017-01-01)<textarea cols="10" rows="1" title="" name="${record.homePage.name}">${position.endDate}</textarea></dd>
                        <br><dd>Должность <textarea cols="20" rows="1" title="" name="${record.homePage.name}">${position.title}</textarea></dd>
                        <dd>Описание <br><textarea cols="100" rows="5" title="" name="${record.homePage.name}">${position.description}</textarea></dd>
                    </c:forEach>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
                    </c:when>
                    <c:otherwise>$</c:otherwise>
                </c:choose>
                <br>
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