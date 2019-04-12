package utilities;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtils {
    private static final SessionFactory mySessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create a service registry based on the Hibernate configuration file for the program
            ServiceRegistry myServiceRegistry =
                    new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();

            // Create Metadata sources using the above registry
            Metadata myMetadata = new MetadataSources(myServiceRegistry).getMetadataBuilder().build();

            // Return the session factory
            return myMetadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            System.out.println("ERROR:  Unable to create initial session factory.\n" + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return mySessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

}