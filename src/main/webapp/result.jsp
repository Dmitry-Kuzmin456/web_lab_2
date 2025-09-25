<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="server.Result" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/duck1.jpg">
    <meta charset="UTF-8">
    <title>Результаты проверки</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
</head>
<body>
<header>
    <h1>Лабораторная работа по вебу №2</h1>
    <table>
        <tr>
            <td class="left"><img src="${pageContext.request.contextPath}/img/duck_without_background_2.png" alt="duck 1" class="duck"></td>
            <td>
                <p>Выполнил: Кузьмин Дмитрий Анатольевич</p>
                <p>Группа: P3209</p>
                <p>Номер варианта: 466402</p>
            </td>
            <td class="right"><img src="${pageContext.request.contextPath}/img/duck_without_background_1.png" alt="duck 2" class="duck"></td>
        </tr>
    </table>
</header>

<main>
    <section class="results_section">
        <table>
            <thead>
            <tr>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Результат</th>
                <th>Время работы (нс)</th>
                <th>Серверное время</th>
            </tr>
            </thead>
            <tbody>
            <%
                Result result = (Result) request.getAttribute("result");
                if (result != null) {
            %>
            <tr>
                <td><%= result.getX() %></td>
                <td><%= result.getY() %></td>
                <td><%= result.getR() %></td>
                <td class="<%= result.isHit() ? "hit" : "miss" %>">
                    <%= result.isHit() ? "Попадание" : "Промах" %>
                </td>
                <td><%= result.getExecTime() %></td>
                <td><%= result.getCurrentTime() %></td>
            </tr>
            <% } %>
            </tbody>
        </table>

        <p style="text-align: center; margin-top: 20px;">
            <a href="<%= request.getContextPath() %>/index.jsp">Вернуться к форме</a>
        </p>
    </section>
</main>
</body>
</html>
