package dev.tuzserik.service.oriented.architecture.lab2.service.one.persistence;

import dev.tuzserik.service.oriented.architecture.lab2.service.one.model.Coordinates;
import dev.tuzserik.service.oriented.architecture.lab2.service.one.model.FuelType;
import dev.tuzserik.service.oriented.architecture.lab2.service.one.model.Vehicle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Datasource {
    private static SessionFactory sessionFactory;
    public static List<Vehicle> cachedVehicles = new ArrayList<>();

    public static void flushCache(Session session) {
        Transaction transaction;
        transaction = session.beginTransaction();

        for (Vehicle vehicle : cachedVehicles)
            session.save(vehicle);

        session.flush();
        cachedVehicles.clear();

        transaction.commit();
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();

                settings.put(Environment.DRIVER, "org.h2.Driver");
                settings.put(Environment.URL, "jdbc:h2:mem:testdb");
                settings.put(Environment.USER, "sa");
                settings.put(Environment.PASS, "password");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.HBM2DDL_AUTO, "create");
                settings.put(Environment.HBM2DDL_CHARSET_NAME, "UTF-8");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(Vehicle.class);
                configuration.addAnnotatedClass(FuelType.class);
                configuration.addAnnotatedClass(Coordinates.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

                return sessionFactory;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return sessionFactory;
    }
}
