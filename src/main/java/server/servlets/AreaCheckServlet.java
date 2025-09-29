package server.servlets;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.AppLogger;
import server.Params;
import server.Result;
import server.ResultsBean;

import java.math.BigDecimal;
import java.util.logging.Logger;


@WebServlet("/request/calculate")
public class AreaCheckServlet extends HttpServlet {
    private static final Logger logger = AppLogger.getLogger(AreaCheckServlet.class);

    @Inject
    private ResultsBean resultsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        requestHandler(request, response);
    }

    private void requestHandler(HttpServletRequest request, HttpServletResponse response) {
        try {
            String x = request.getParameter("x");
            String y = request.getParameter("y");
            String r = request.getParameter("r");
            String action = request.getParameter("action");
            if ("click".equals(action)){
                this.clickRequest(x, y, r, request, response);
            } else{
                this.request(x, y, r, request, response);
            }



        } catch (Exception e) {
            logger.info("error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void request(String x, String y, String r, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Params params = new Params(x, y, r);

        if (params.hasErrors()) {
            request.setAttribute("serverError", params.getErrors());
            logger.info("error: " + params.getErrors());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        long start = System.nanoTime();

        boolean hit = this.checkRequestHit(params.getX(), params.getY(), params.getR());
        long execTime = System.nanoTime() - start;
        String currentTime = java.time.LocalDateTime.now().toString();

        Result result = new Result(params.getX(), y, params.getR(), hit, execTime, currentTime);

        resultsBean.addResult(result);

        request.setAttribute("result", result);

        logger.info("result: " + result.toJson());

        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }

    private void clickRequest(String sx, String sy, String sr, HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            double x = Double.parseDouble(sx);
            double y = Double.parseDouble(sy);
            byte r = Byte.parseByte(sr);
            boolean hit = checkHit(x, y, r);
            response.setContentType("application/json");
            response.getWriter().write(String.format("{\"x\":%s,\"y\":%s,\"hit\":%s}", x, y, hit));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(400, "Некорректные координаты");
        }
    }

    private boolean checkRequestHit(byte x, BigDecimal y, byte r) {
        double xx = x / r;
        double yy = y.doubleValue() / r;
        return checkHit(xx, yy, r);
    }

    private <T extends Number> boolean checkHit(T xx, T yy, T rr) {
        double x = xx.doubleValue();
        double y = yy.doubleValue();
        double absX = Math.abs(x);

        // верхняя граница
        double upperY = 0;
        if (absX < 0.5) upperY = 2.25;
        else if (absX < 0.75) upperY = 3 * absX + 0.75;
        else if (absX < 1) upperY = 9 - 8 * absX;
        else if (absX <= 3) upperY = 1.5 - 0.5 * absX - (6 * Math.sqrt(10) / 14) * (Math.sqrt(3 - x*x + 2 * absX) - 2);
        else if (absX <= 7) upperY = 3 * Math.sqrt(1 - Math.pow(x / 7, 2));

        // нижняя граница
        double lowerY = 0;
        if (absX > 4) lowerY = -3 * Math.sqrt(1 - Math.pow(x / 7, 2));
        else if (absX <= 4) lowerY = Math.abs(x / 2) - (3 * Math.sqrt(33) - 7) / 112 * x * x - 3 + Math.sqrt(1 - Math.pow(Math.abs(absX - 2) - 1, 2));

        return y >= lowerY && y <= upperY;
    }
}
