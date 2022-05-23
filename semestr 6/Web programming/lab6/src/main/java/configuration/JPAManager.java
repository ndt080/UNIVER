package configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class JPAManager {
    private static final String persistenceName = "com.application.racing";
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceName);

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void close() {
        entityManagerFactory.close();
    }
}

