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

@WebServlet(name = "distanceTravelledSearcher", value = "/distance-travelled-searcher")
public class DistanceTravelledSearcher extends HttpServlet {
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
                    gson.toJson(QueryBuilder.getPreparedQueryDistanceTraveledLessThan(
                            session, request.getParameter("distance-travelled-less-than")
                    ).getResultList())
            );
        }
        catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }
}
