package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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
            String z = request.getParameter("z");

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(
                    "<html><body>" +
                            "<h2>Результаты проверки:</h2>" +
                            "<p>x = " + x + "</p>" +
                            "<p>y = " + y + "</p>" +
                            "<p>z = " + z + "</p>" +
                            "</body></html>"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
