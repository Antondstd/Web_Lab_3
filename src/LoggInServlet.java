import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class LoggInServlet {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();
//    private  Session entityManager = SessionFactoryUtil.getEntityManager();

    public void loggin() {
//        String createPOINTS = new String("Create TABLE IF NOT EXISTS POINTS(id SERIAL PRIMARY KEY," +
//                "X real," +
//                "Y real," +
//                "user_id real)");
//        String createUSERS = new String("Create TABLE IF NOT EXISTS USERS(id SERIAL PRIMARY KEY," +
//                "name varchar(255))");
//        String createRESULTS = new String("Create TABLE IF NOT EXISTS results(id SERIAL PRIMARY KEY," +
//                "R real," +
//                "resultshot real," +
//                "thedate real," +
//                "point_id real)");
        String createPOINTS = new String("Create TABLE POINTS(id SERIAL PRIMARY KEY," +
                "X real," +
                "Y real," +
                "user_id real)");
        String createUSERS = new String("Create TABLE USERS(id SERIAL PRIMARY KEY, name varchar(255))");
        String createRESULTS = new String("Create TABLE results(id SERIAL PRIMARY KEY," +
                "R real," +
                "resultshot real," +
                "thedate real," +
                "point_id real)");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        Userses userses =  (Userses) session.getAttribute("Userses");
        try {
            Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String sName = requestParameterMap.get("CreateLoggin:name");
            System.out.println("sid: " + sName);
            User user = new User();
            user.setName(sName);
            entityManager.getTransaction().begin();
//                entityManager.createNativeQuery(createPOINTS).executeUpdate();
//            entityManager.createNativeQuery(createUSERS).executeUpdate();
//            entityManager.createNativeQuery(createRESULTS).executeUpdate();
            User us = null;
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> cquery = builder.createQuery(User.class);
            Root<User> root = cquery.from( User.class );
            cquery.select( root );
            cquery.where(builder.equal( root.get("name"), sName ));
            try {
//                us = (User) entityManager.createQuery("Select e from User e where e.name=:nam").setParameter("nam", sName).getSingleResult();
                us = (User) entityManager.createQuery(cquery).getSingleResult();
            }
            catch (NoResultException e){
                us = null;
            }
            if (us != null){
                userses.setId(us.getId());
                userses.setName(us.getName());
            }
            else {
                user = (User)entityManager.merge(user);
                userses.setId(user.getId());
                userses.setName(user.getName());
            }
            System.out.println("USER ID:" + userses.getId());
            entityManager.getTransaction().commit();
            facesContext.getExternalContext().redirect("points_page.xhtml");
        } catch (
                Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
}