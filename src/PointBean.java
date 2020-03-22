import javax.annotation.Resource;
import javax.faces.annotation.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import java.util.*;

import org.hibernate.Session;
import org.primefaces.context.RequestContext;

public class PointBean {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();
    private int st = 0;

    public void setSt(int st) {
        this.st = st;
    }
    @Resource UserTransaction ut;
    public int testSt() {
        int i = 0;
        try {
            ut.begin();
            i++;
//            if (i == 2)
//            {
//                throw new Exception();
//            }
            System.out.println("TestTran");
            ut.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            try {
                ut.rollback();
            }
            catch (Exception xe)
            {

            }
        }
        return i;
    }
    //    private Session entityManager = SessionFactoryUtil.getEntityManager();

    public void addPoint(double x, double y, double r, int shot) {

        try {
            entityManager.getTransaction().begin();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            Userses userses = (session != null) ? (Userses) session.getAttribute("Userses") : null;
            System.out.println("name: " + userses.getName() + " id: " + userses.getId());
            if (userses == null || userses.getId() == 0 || userses.getName() == null){
                throw new Exception();
            }
            User uses = (User)entityManager.createQuery("SELECT e from User e where e.name=:name and e.id =:id").setParameter("id",userses.getId()).setParameter("name",userses.getName()).getSingleResult();
            Point p = new Point();
            p.setX(x);
            p.setY(y);
            Result res = new Result();
            res.setR(r);
            res.setResultShot(shot);
            res.setTheDate(new Date());
            p.setUser(uses);
            p.addResult(res);
            uses.addPoint(p);
            /*if (!entityManager.contains(p))*/
            entityManager.merge(uses);
            entityManager.getTransaction().commit();
            //user.removePoint(p);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("drawPoint(" + x + "," + y + "," + r + "," + shot + ")");

        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public int addClickPoint() {
        int shot = 4;
        try {

            Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String sx = requestParameterMap.get("PointX");
            String sy = requestParameterMap.get("PointY");
            String sr = requestParameterMap.get("PointR");
            double x = Double.parseDouble(sx);
            double y = Double.parseDouble(sy);
            double r = Double.parseDouble(sr);
            shot = getShot(x, y, r);
            addPoint(x, y, r, shot);
        } catch (Exception e) {
            return shot;
        }
        return shot;
    }

    public void addPagePoint() {
        try {
            Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String sx = requestParameterMap.get("PointForm:variableX").replace(',', '.');
            String sy = requestParameterMap.get("PointForm:variableY").replace(',', '.');
            String sr = requestParameterMap.get("PointForm:variableR").replace(',', '.');
            double x = Double.parseDouble(sx);
            double y = Double.parseDouble(sy);
            double r = Double.parseDouble(sr);

            addPoint(x, y, r, getShot(x, y, r));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getShot(double x, double y, double r) {
        if (x < -3 || x > 5 || y < -5 || y > 3) {
            return 3; //Вне ОДЗ
        }
        if (x >= -r / 2 && x <= 0 && y <= 0 && y >= -r / 2 && x * x + y * y <= r * r || x >= 0 && x <= r && y >= 0 && y <= r / 2 || x >= 0 && x <= r && y <= 0 && y >= -r / 2 && x - 2 * y <= r)
            return 1; //Попала
        else return 2; //Не попала

    }

    public void getList() {
        try {
            Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String sr = requestParameterMap.get("Radius");
            double R = Double.parseDouble(sr);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            Userses userses = (session != null) ? (Userses) session.getAttribute("Userses") : null;
            userses.setCurrentR(R);
            entityManager.getTransaction().begin();
            @SuppressWarnings("unchecked")
            //User uses = (User)entityManager.createQuery("SELECT e from User e where e.name=:name and e.id =:id").setParameter("id",user.getId()).setParameter("name",user.getName()).getSingleResult();
            List<Point> pointsList = entityManager.createQuery("SELECT  e from Point e where e.user.id=:user").setParameter("user", userses.getId()).getResultList();
            String datasa = new String("");
            int idatas = 0;
            //System.out.println("Size: " + pointsList.size());
            if (pointsList.size() > 0) {

                RequestContext context = RequestContext.getCurrentInstance();
                for (Point pointS : pointsList) {
                    idatas++;
                    int itsShot = getShot(pointS.getX(), pointS.getY(), R);
                    context.execute("drawPoint(" + pointS.getX() + "," + pointS.getY() + "," + R + "," + itsShot + ")");
                    Result r = new Result();
                    r.setR(R);
                    r.setResultShot(itsShot);
                    r.setTheDate(new Date());
                    //if (!pointS.getResults().contains(r)) {
                        pointS.addResult(r);
                        //uses.addPoint(pointS);
                        entityManager.merge(pointS);
                    //}

//                    if (idatas < pointsList.size()) {
//                        datasa = datasa.concat("{\"x\":\"" + pointS.getX() + "\", \"y\":\"" + pointS.getY() + "\",\"r\":\"" + pointS.getR() + "\",\"result\":\"" + itsShot + "\"},");
//                    } else {
//                        datasa = datasa.concat("{\"x\":\"" + pointS.getX() + "\", \"y\":\"" + pointS.getY() + "\",\"r\":\"" + pointS.getR() + "\",\"result\":\"" + itsShot + "\"}");
//                    }
                }
            }
                entityManager.getTransaction().commit();
//            System.out.println("TAL: " + pointsList.size());
//            if (pointsList.size() > 0) {
//                RequestContext context = RequestContext.getCurrentInstance();
//                context.execute("drawAlotOfPoints([ " + datasa + "])");
//            }
            } catch(Exception e){
            e.printStackTrace();
                entityManager.getTransaction().rollback();
            }
        }

        public List<Result> getTableList(){
        List<Result> resultList;

            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            Userses userses = (session != null) ? (Userses) session.getAttribute("Userses") : null;
            System.out.println("Current R: " + userses.getCurrentR() + "UserName: " + userses.getName());
            entityManager.getTransaction().begin();

//            pointsList = entityManager.createQuery("SELECT  e from Point e where e.userName=:user").setParameter("user", user.getId()).getResultList();
            resultList = entityManager.createQuery("SELECT  res from Result res where res.point.user.id=:user and res.r =:curR order by res.theDate asc").setParameter("user", userses.getId()).setParameter("curR", userses.getCurrentR()).getResultList();
            RequestContext context = RequestContext.getCurrentInstance();
            if (resultList.size() > 0) context.execute("unhidden()");
            Result prevRes = new Result();
//            for (Result r : resultList){
//                System.out.println("x: " + r.getPoint().getX() + " y: " + r.getPoint().getY() + " r: " + r.getR() + " ,time: " + r.getTheDate().toString());
//                System.out.println("id: " + r.getId() + " hashcode:" + r.hashCode());
//                System.out.println("equals with previous: " + r.equals(prevRes));
//                prevRes = r;
//
//            }
            entityManager.getTransaction().commit();
            Collections.reverse(resultList);
        return resultList;
        }
    }
