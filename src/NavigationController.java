import javax.faces.context.FacesContext;
import java.io.Serializable;

public class NavigationController implements Serializable {
    private static final long serialVersionUID = 1L;
    private String pageName;

    public String moveToHome(){
        return "home";
    }
    public String moveToPagePoints(){
        return "page_points";
    }

    public String showPage() {

        if(pageName.equals("points_page")) {
            return "page_points";
        }else {
            return "home";
        }
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
}
