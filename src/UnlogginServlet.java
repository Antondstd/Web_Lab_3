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

@WebServlet("/unlogin")
public class UnlogginServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Userses user = (session != null) ? (Userses) session.getAttribute("Userses") : null;
        user = null;
        ServletContext servletContext = getServletContext();
                RequestDispatcher reqDis = servletContext.getRequestDispatcher("/home.xhtml");
                reqDis.forward(req, resp);
    }
}