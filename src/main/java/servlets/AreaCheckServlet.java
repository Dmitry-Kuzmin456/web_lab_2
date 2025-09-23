package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/request/calculate")
public class AreaCheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        requestHandler(request, response);
    }

    private void requestHandler(HttpServletRequest request, HttpServletResponse response) {
        try {
            String x = request.getParameter("x");
            String y = request.getParameter("y");
            String r = request.getParameter("r");

            // Простейшая проверка серверной ошибки
            if (x == null || y == null || r == null || x.isEmpty() || y.isEmpty() || r.isEmpty()) {
                request.setAttribute("serverError", "Ошибка: какие-то значения не переданы!");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                return;
            }

            // Пример проверки конкретного значения
            if (x.equals("2")) { // допустим, сервер считает это ошибкой
                request.setAttribute("serverError", "Ошибка: x = 2 запрещено на сервере");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                return;
            }

            // Всё ок, передаем на result.jsp
            request.setAttribute("x", x);
            request.setAttribute("y", y);
            request.setAttribute("r", r);
            request.setAttribute("hit", true); // сюда ставьте вашу логику попадания
            request.setAttribute("execTime", 12345); // пример времени
            request.setAttribute("currentTime", java.time.LocalDateTime.now().toString());

            request.getRequestDispatcher("/result.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
