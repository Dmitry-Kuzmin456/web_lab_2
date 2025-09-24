package server.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/request")
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        requestHandler(request, response);
    }

    private void requestHandler(HttpServletRequest request, HttpServletResponse response){
        try{
            String x = request.getParameter("x");
            String y = request.getParameter("y");
            String r = request.getParameter("r");
            String action = request.getParameter("action");
            System.out.println(x + " " + y + " " + r);

            if (x != null && y != null && r != null && !x.isEmpty() && !y.isEmpty() && !r.isEmpty()) {

                request.getRequestDispatcher("/request/calculate").forward(request, response);
            }
            else {
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
