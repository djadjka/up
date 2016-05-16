import by.bsu.chat.Log;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("password");
        if (UserData.getInstance().compareLoginPass(login, pass)) {
            Cookie userIdCookie = new Cookie("login", login);
            userIdCookie.setMaxAge(5000);
            resp.addCookie(userIdCookie);
            resp.sendRedirect("/index.html");
            Log.getInstance().addInformation(login + " :authorization successful");
        } else {
            Log.getInstance().addInformation(login + " :Incorrect login or password");
            req.setAttribute("infMes", "Incorrect login or password");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

    }
}
