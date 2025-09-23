<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Результаты проверки</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        table { border-collapse: collapse; margin-top: 20px; }
        td, th { border: 1px solid #333; padding: 8px 12px; }
        .hit { color: green; font-weight: bold; }
        .miss { color: red; font-weight: bold; }
    </style>
</head>
<body>
<h2>Результаты проверки</h2>
<table>
    <tr>
        <th>X</th>
        <th>Y</th>
        <th>R</th>
        <th>Результат</th>
        <th>Время выполнения (нс)</th>
        <th>Серверное время</th>
    </tr>
    <tr>
        <td>${x}</td>
        <td>${y}</td>
        <td>${r}</td>
        <td class="${hit ? "hit" : "miss"}">
            ${hit ? "Попадание" : "Промах"}
        </td>
        <td>${execTime}</td>
        <td>${currentTime}</td>
    </tr>
</table>

<p><a href="${pageContext.request.contextPath}/index.jsp">Вернуться к форме</a></p>
</body>
</html>
