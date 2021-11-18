package dev.tuzserik.service.oriented.architecture.lab1.server.persistence;

import dev.tuzserik.service.oriented.architecture.lab1.server.model.Coordinates;
import dev.tuzserik.service.oriented.architecture.lab1.server.model.FuelType;
import dev.tuzserik.service.oriented.architecture.lab1.server.model.Vehicle;
import org.hibernate.SessionFactory;
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

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.HBM2DDL_AUTO, "create");
                settings.put(Environment.HBM2DDL_CHARSET_NAME, "UTF-8");
                settings.put(Environment.DATASOURCE, "java:/PostgresDS");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(Vehicle.class);
                configuration.addAnnotatedClass(FuelType.class);
                configuration.addAnnotatedClass(Coordinates.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                return sessionFactory;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }
}
