import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
@Entity
@Table(name = "Results")
public class Result implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;
    @Column(name = "r")
    private double r;
    @Column(name = "resultshot")
    private int resultShot;
    @Column(name = "thedate")
    private Date theDate;
    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Point point;

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public int getId() {
        return id;
    }

    public double getR() {
        return r;
    }

    public int getResultShot() {
        return resultShot;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setResultShot(int resultShot) {
        this.resultShot = resultShot;
    }

    public void setTheDate(Date theDate) {
        this.theDate = theDate;
    }

    public Date getTheDate() {
        return theDate;
    }

    public String StringShot(){
        if (resultShot == 1) return "Попала";
        else if (resultShot == 2) return "Не попала";
        else return "Вне ОДЗ";
    }

    public String StringDate(){
        SimpleDateFormat dt = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
        return dt.format(theDate);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;

        return Double.compare(result.r, r) == 0 &&
                Double.compare(result.resultShot, resultShot) == 0/* && point.getUserName().equals(result.getPoint().getUserName())*/;
    }

    @Override
    public int hashCode() {
        int hash = Double.valueOf(r).hashCode() + Integer.valueOf(resultShot).hashCode()/* + point.getUserName().hashCode()*/;
        return hash;

    }
}
