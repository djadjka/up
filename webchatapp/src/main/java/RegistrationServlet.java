import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(value = "/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("password");
        if (!UserData.getInstance().compareLoginPass(login, pass)) {
            UserData.getInstance().addUserData(login, pass);
            req.setAttribute("infMes", "Registration complied");
        } else {
            req.setAttribute("infMes", "The user with the same name is already registered");
        }
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
