package server.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import server.Params;


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

            Params params = new Params(x, y, r);

            if (params.hasErrors()) {
                request.setAttribute("serverError", params.getErrors());
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
