import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Date;

public class TestView implements Serializable {

    private Date dt;

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    @PostConstruct
    public void st(){
        this.dt = new Date();
    }
}
