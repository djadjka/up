import by.bsu.chat.Log;

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
        if (login.equals("") || pass.equals("")) {
            Log.getInstance().addInformation("One of the boxes empty");
            req.setAttribute("infMes", "One of the boxes empty, repeat again");
        } else if (!UserData.getInstance().compareLoginPass(login, pass)) {
            Log.getInstance().addInformation(login + ": Registration complied");
            UserData.getInstance().addUserData(login, pass);
            req.setAttribute("infMes", "Registration complied");
        } else {
            Log.getInstance().addInformation(login + ": The user with the same name is already registered");
            req.setAttribute("infMes", "The user with the same name is already registered");
        }
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
