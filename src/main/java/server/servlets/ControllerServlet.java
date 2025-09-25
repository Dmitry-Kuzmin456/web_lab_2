package server.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.Result;

@WebServlet("/request")
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        requestHandler(request, response);
    }

    private void requestHandler(HttpServletRequest request, HttpServletResponse response){
        try{
            String xStr = request.getParameter("x");
            String yStr = request.getParameter("y");
            String rStr = request.getParameter("r");


            if (xStr != null && yStr != null && rStr != null &&
                    !xStr.isEmpty() && !yStr.isEmpty() && !rStr.isEmpty()) {
                request.getRequestDispatcher("/request/calculate").forward(request, response);
            } else {
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
