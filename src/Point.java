import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
@Table(name = "Points")
public class Point implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;
    @Column(name = "x")
    private double x;
    @Column(name = "y")
    private double y;
    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Result> results = new HashSet<Result>();

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void addResult(Result res) {
        results.add(res);
        res.setPoint(this);
    }

    public void removeResult(Result res) {
        results.remove(res);
        res.setPoint(null);
    }


    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setResults(Set<Result> results) {
        this.results = results;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Set<Result> getResults() {
        return results;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point p = (Point) o;

        return Double.compare(x, p.getX()) == 0 &&
                Double.compare(y, p.getY()) == 0 /*&& user.getName().equals(p.getUser().getName())*/;
    }

    @Override
    public int hashCode() {
        int hash = Double.valueOf(x).hashCode() + Double.valueOf(y).hashCode()/* + user.getName().hashCode()*/;
        return hash;

    }
}
