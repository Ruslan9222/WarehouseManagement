package by.ruslanradevich.warehousemanagement.util;

import by.ruslanradevich.warehousemanagement.config.ConfigLoader;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Properties properties = new Properties();
            properties.put("hibernate.dialect", ConfigLoader.get("hibernate.dialect"));
            properties.put("hibernate.show_sql", ConfigLoader.get("hibernate.show_sql"));
            properties.put("hibernate.format_sql", ConfigLoader.get("hibernate.format_sql"));
            properties.put("hibernate.hbm2ddl.auto", ConfigLoader.get("hibernate.hbm2ddl.auto"));
            properties.put("hibernate.connection.driver_class", ConfigLoader.get("datasource.driver-class-name"));
            properties.put("hibernate.connection.url", ConfigLoader.get("datasource.url"));
            properties.put("hibernate.connection.username", ConfigLoader.get("datasource.username"));
            properties.put("hibernate.connection.password", ConfigLoader.get("datasource.password"));

            sessionFactory = new Configuration()
                    .addProperties(properties)
                    .addPackage("by.ruslanradevich.warehousemanagement.entity")
                    .addAnnotatedClass(by.ruslanradevich.warehousemanagement.entity.User.class)
                    .addAnnotatedClass(by.ruslanradevich.warehousemanagement.entity.Warehouse.class)
                    .addAnnotatedClass(by.ruslanradevich.warehousemanagement.entity.Client.class)
                    .addAnnotatedClass(by.ruslanradevich.warehousemanagement.entity.Product.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
