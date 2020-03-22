import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(false);
        Userses user = (session != null) ? (Userses) session.getAttribute("Userses") : null;
        if (user == null || user.validation().equals("fail")) {

            RequestDispatcher reqDis = req.getRequestDispatcher("/home.xhtml");
            reqDis.forward(req, resp);
        }



        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
