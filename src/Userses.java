import java.io.Serializable;

public class Userses implements Serializable {
    private int id;
    private String name;

    private PointBean pointBean;

    public void setPointBean(PointBean pointBean) {
        this.pointBean = pointBean;
    }

    public PointBean getPointBean() {
        return pointBean;
    }

    private double currentR;

    public void setCurrentR(double currentR) {
        this.currentR = currentR;
    }

    public double getCurrentR() {
        return currentR;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String validation(){
        if (name == null) return "fail";
        else return "success";
    }
}
