package dev.tuzserik.service.oriented.architecture.lab1.server.servlets;

import com.google.gson.Gson;
import dev.tuzserik.service.oriented.architecture.lab1.server.utils.QueryBuilder;
import dev.tuzserik.service.oriented.architecture.lab1.server.persistence.Datasource;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "distanceCalculator", value = "/distance-calculator")
public class DistanceCalculator extends HttpServlet {
    private Gson gson;

    @Override
    public void init() {
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Session session = Datasource.getSessionFactory().openSession()) {
            session.save(Datasource.cachedVehicles);
            Datasource.cachedVehicles.clear();

            response.setStatus(200);
            response.getWriter().write(
                    gson.toJson(QueryBuilder.getPreparedQueryDistanceSum(session).getSingleResult())
            );
        }
        catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }
}
