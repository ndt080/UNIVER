package connection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtility {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("restaurant");;
    private static boolean flag= false;
    private static EntityManager em;
    public static EntityManager getEntityManager() {
        if(!flag) {
            em = entityManagerFactory.createEntityManager();
            return em;
        }
        else
            return em;
    }

    public static void close() {
        entityManagerFactory.close();
    }
}
