import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
    private int beg = 0;
    private static final EntityManagerFactory entityManagerFactory;
    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("test");

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();

    }

    private void begg(){
        String createPOINTS = new String("Create TABLE IF NOT EXISTS POINTS(id SERIAL PRIMARY KEY," +
                "X real," +
                "Y real," +
                "user_id real,");
        String createUSERS = new String("Create TABLE IF NOT EXISTS USERS(id SERIAL PRIMARY KEY," +
                "name varchar(255)");
        String createRESULTS = new String("Create TABLE IF NOT EXISTS USERS(id SERIAL PRIMARY KEY," +
                "R real," +
                "resultshot real," +
                "thedate real," +
                "point_id real");





    }
}