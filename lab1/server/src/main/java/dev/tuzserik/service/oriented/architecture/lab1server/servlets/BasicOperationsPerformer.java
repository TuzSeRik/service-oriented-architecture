package dev.tuzserik.service.oriented.architecture.lab1server.servlets;

import com.google.gson.Gson;
import dev.tuzserik.service.oriented.architecture.lab1server.model.Vehicle;
import dev.tuzserik.service.oriented.architecture.lab1server.persistence.Datasource;
import dev.tuzserik.service.oriented.architecture.lab1server.utils.QueryBuilder;
import dev.tuzserik.service.oriented.architecture.lab1server.utils.VehicleParameters;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "basicOperationsPerformer", value = "/basic-operations-performer")
public class BasicOperationsPerformer extends HttpServlet {
    private List<Vehicle> vehicleList;
    private Gson gson;

    public void init() {
        vehicleList = new ArrayList<>();
        gson = new Gson();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Transaction transaction = null;

        try (Session session = Datasource.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            VehicleParameters parameters = new VehicleParameters(request);

            Vehicle vehicle =
                    new Vehicle()
                            .setName(parameters.getName())
                            .setCoordinates(parameters.getCoordinates())
                            .setEnginePower(parameters.getEnginePower())
                            .setNumberOfWheels(parameters.getNumberOfWheels())
                            .setDistanceTravelled(parameters.getDistanceTravelled())
                            .setFuelType(parameters.getFuelType())
                    ;

            vehicleList.add(vehicle);

            transaction.commit();

            response.setStatus(200);
            response.getWriter().write(gson.toJson(vehicle));
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

            response.sendError(500);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            if (request.getParameter("id-fast") != null) {
                List<Vehicle> filteredVehicles =
                        vehicleList.stream().filter(vehicle -> vehicle.getId() ==
                                Integer.parseInt(request.getParameter("id-fast"))).collect(Collectors.toList());

                if (filteredVehicles.size() == 1) {
                    response.setStatus(200);
                    response.getWriter().write(gson.toJson(filteredVehicles.get(0)));
                }
                else {
                    try (Session session = Datasource.getSessionFactory().openSession()) {
                        List<Vehicle> particularVehicle = QueryBuilder.getPreparedQuerySingleId(
                                session, request.getParameter("id-fast")
                        ).getResultList();

                        if (particularVehicle.size() == 1) {
                            response.setStatus(200);
                            response.getWriter().write(gson.toJson(vehicleList.get(0)));
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        response.sendError(500);
                    }

                    response.sendError(500);
                }
            }
            else {
                try (Session session = Datasource.getSessionFactory().openSession()) {
                    session.save(vehicleList);
                    vehicleList.clear();
                    List<Vehicle> allFilteredVehicles = QueryBuilder.getPreparedQuery(request, session).getResultList();
                    response.setStatus(200);
                    response.getWriter().write(gson.toJson(allFilteredVehicles));
                }
                catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(500);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = "";
        if (protocol.endsWith("1.1")) {
            resp.sendError(405, msg);
        } else {
            resp.sendError(400, msg);
        }

    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = "";
        if (protocol.endsWith("1.1")) {
            resp.sendError(405, msg);
        } else {
            resp.sendError(400, msg);
        }

    }

    public void destroy() {
        try (Session session = Datasource.getSessionFactory().openSession()) {
            session.save(vehicleList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
