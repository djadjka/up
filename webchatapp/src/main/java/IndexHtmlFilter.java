import by.bsu.chat.Log;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(value = "/index.html")
public class IndexHtmlFilter implements Filter {
    public void init(FilterConfig filterConfig)
            throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            Cookie[] cookies = ((HttpServletRequest) request).getCookies();
            for (Cookie item : cookies) {
                if (UserData.getInstance().checkLogin(item.getValue())) {
                    chain.doFilter(request, response);
                }
            }
        }catch (NullPointerException e){
            Log.getInstance().addException("Cookie not found");
        }
        Log.getInstance().addInformation("Unauthorized");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    public void destroy() {

    }


}