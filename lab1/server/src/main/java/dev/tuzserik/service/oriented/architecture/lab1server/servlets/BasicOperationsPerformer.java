package dev.tuzserik.service.oriented.architecture.lab1server.servlets;

import com.google.gson.Gson;
import dev.tuzserik.service.oriented.architecture.lab1server.model.Vehicle;
import dev.tuzserik.service.oriented.architecture.lab1server.persistence.Datasource;
import dev.tuzserik.service.oriented.architecture.lab1server.utils.QueryBuilder;
import dev.tuzserik.service.oriented.architecture.lab1server.utils.VehicleParameters;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "basicOperationsPerformer", value = "/basic-operations-performer")
public class BasicOperationsPerformer extends HttpServlet {
    private Gson gson;

    @Override
    public void init() {
        gson = new Gson();
    }

    @Override
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

            Datasource.cachedVehicles.add(vehicle);

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

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            if (request.getParameter("id-fast") != null) {
                List<Vehicle> filteredVehicles =
                        Datasource.cachedVehicles.stream().filter(vehicle -> vehicle.getId() ==
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
                            response.getWriter().write(gson.toJson(Datasource.cachedVehicles.get(0)));
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
                    session.save(Datasource.cachedVehicles);
                    Datasource.cachedVehicles.clear();
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

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Transaction transaction = null;

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

        vehicle.setId(Long.parseLong(request.getParameter("id")));

        List<Vehicle> filteredVehicle = Datasource.cachedVehicles.stream().filter(v -> v.getId() == vehicle.getId()).collect(Collectors.toList());

        if (!filteredVehicle.isEmpty()) {
            Datasource.cachedVehicles.set(Datasource.cachedVehicles.indexOf(filteredVehicle.get(0)), vehicle);
        }
        else {
            try (Session session = Datasource.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                session.save(vehicle);
                transaction.commit();

                response.setStatus(200);
                response.getWriter().write(gson.toJson(vehicle));
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();

                response.sendError(500);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Transaction transaction = null;
        long id = Long.parseLong(request.getParameter("id"));

        List<Vehicle> filteredVehicle = Datasource.cachedVehicles.stream().filter(v -> v.getId() == id).collect(Collectors.toList());

        if (!filteredVehicle.isEmpty()) {
            Datasource.cachedVehicles.remove(filteredVehicle.get(0));
        }
        else
            try (Session session = Datasource.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                session.delete(id);
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();

                response.sendError(500);
            }
    }

    @Override
    public void destroy() {
        Transaction transaction = null;

        try (Session session = Datasource.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(Datasource.cachedVehicles);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
