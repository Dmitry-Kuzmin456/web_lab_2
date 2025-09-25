<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/duck1.jpg">
    <meta charset="UTF-8">
    <title>Web_lab_1</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
    <script>
        const contextPath = '<%= request.getContextPath() %>';
    </script>
    <script defer src="${pageContext.request.contextPath}/script.js"></script>
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
    <section class="input_section">
        <form id="pointForm" method="get" action="${pageContext.request.contextPath}/request">
            <table>
                <tr>
                    <td width="50%">
                        <div class="form-row" style="position: relative;">
                            <span class="form-label">X:</span>
                            <input type="button" name="x" value="-5" class="x-button">
                            <input type="button" name="x" value="-4" class="x-button">
                            <input type="button" name="x" value="-3" class="x-button">
                            <input type="button" name="x" value="-2" class="x-button">
                            <input type="button" name="x" value="-1" class="x-button">
                            <input type="button" name="x" value="0" class="x-button">
                            <input type="button" name="x" value="1" class="x-button">
                            <input type="button" name="x" value="2" class="x-button">
                            <input type="button" name="x" value="3" class="x-button">
                            <input type="hidden" name="x" id="xInput">
                            <span id="errorX" class="error-tooltip"></span>
                        </div>

                        <div class="form-row" style="position: relative;">
                            <span class="form-label">Y:</span>
                            <input type="text" name="y" placeholder="Введите Y (-5...5)" style="width: 150px;">
                            <span id="errorY" class="error-tooltip"></span>
                        </div>

                        <div class="form-row" style="position: relative;">
                            <span class="form-label">R:</span>
                            <input type="radio" name="r" value="1" id="r1"> <label for="r1">1</label>
                            <input type="radio" name="r" value="2" id="r2"> <label for="r2">2</label>
                            <input type="radio" name="r" value="3" id="r3"> <label for="r3">3</label>
                            <input type="radio" name="r" value="4" id="r4"> <label for="r4">4</label>
                            <input type="radio" name="r" value="5" id="r5"> <label for="r5">5</label>
                            <span id="errorR" class="error-tooltip"></span>
                        </div>

                        <div class="form-row" style="position: relative;">
                            <button id="submitBtn" type="submit">Отправить</button>
                            <div id="serverError" class="server-error-tooltip"></div>

                            <% if (request.getAttribute("serverError") != null) { %>
                            <script>
                                const serverError = document.getElementById("serverError");

                                let errorText = `<%= request.getAttribute("serverError").toString().replace("\n", "\\n") %>`;
                                errorText = errorText.replace(/\\n+$/, "");
                                errorText = errorText.replace(/\\n/g, "<br>");
                                serverError.innerHTML = errorText;
                                serverError.classList.add("visible");
                            </script>
                            <% } %>

                        </div>
                    </td>

                    <td width="50%">
                        <div class="graph-container">
                            <canvas id="graphCanvas" width="400" height="400"></canvas>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </section>


    <section class="results_section">
        <table id="resultsTable">
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
                server.ResultsBean resultsBean = (server.ResultsBean) session.getAttribute("resultsBean");
                if (resultsBean != null) {
                    for (server.Result res : resultsBean.getResults()) {
            %>
            <tr>
                <td><%= res.getX() %></td>
                <td><%= res.getY() %></td>
                <td><%= res.getR() %></td>
                <td><%= res.isHit() ? "Попал" : "Мимо" %></td>
                <td><%= res.getExecTime() %></td>
                <td><%= res.getCurrentTime() %></td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>

        </table>
    </section>
</main>
</body>
</html>
