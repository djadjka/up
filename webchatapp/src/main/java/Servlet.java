import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by djadjka.by on 23.04.16.
 */
@WebServlet(value = "/vars")
public class Servlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("password");
        System.out.println(login+"  "+pass);
        if(login.equals("abc")&&pass.equals("abc")){
            req.getRequestDispatcher("index.html").forward(req, resp);
        }
        else{
            req.setAttribute("name","name");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }

    }
}
