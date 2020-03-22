
import javax.faces.context.FacesContext;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
@Entity
@Table(name = "Users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Point> points = new HashSet<Point>();

//    public void putR(){
//        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        String sr = requestParameterMap.get("Radius");
//        double R = Double.parseDouble(sr);
//        System.out.println("Adding R: " + R);
//        this.currentR = R;
//    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPoints(Set<Point> points) {
        this.points = points;
    }

    public void addPoint(Point p) {
        points.add(p);
        p.setUser(this);
    }

    public void removePoint(Point p) {
        points.remove(p);
        p.setUser(null);
    }

    public Set<Point> getPoints() {
        return points;
    }
}
