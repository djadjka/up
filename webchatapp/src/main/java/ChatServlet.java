import by.bsu.chat.*;
import by.bsu.chat.InMemoryMessageStorage;
import by.bsu.chat.Message;
import by.bsu.chat.MessageStorage;
import by.bsu.chat.Portion;
import org.json.simple.parser.ParseException;
import utils.MessageHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet(value = "/chat")
public class ChatServlet extends HttpServlet {
    private MessageStorage messageStorage;
    private final String BAD_REQUEST = " 400 Bad Request";

    @Override
    public void init() throws ServletException {
        super.init();
        messageStorage = new InMemoryMessageStorage();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        try {
            resp.setCharacterEncoding("UTF-8");
            int index = MessageHelper.parseToken(token);
            Portion portion = new Portion(index);
            List<Message> messages = messageStorage.getPortion(portion);
            String responseBody = MessageHelper.buildServerResponseBody(messages, messageStorage.size());
            PrintWriter out = resp.getWriter();
            out.print(responseBody);
            out.flush();
            Log.getInstance().addInformation("GET STATUS:200");
        } catch (Exception e) {
            Log.getInstance().addException("GET" + BAD_REQUEST);
            resp.sendError(400, "Bad Request");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Message message = MessageHelper.getPostMessage(req);
            messageStorage.addMessage(message);
            Log.getInstance().addInformation("POST STATUS:200");
        } catch (ParseException e) {
            Log.getInstance().addException("POST" + BAD_REQUEST);
            resp.sendError(400, "Bad Request");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Message message = MessageHelper.getPutMessage(req);
            messageStorage.addMessage(message);
            Log.getInstance().addInformation("PUT STATUS:200");
        } catch (ParseException e) {
            Log.getInstance().addException("PUT" + BAD_REQUEST);
            resp.sendError(400, "Bad Request");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Message message = MessageHelper.getDelMessage(req);
            messageStorage.addMessage(message);
            Log.getInstance().addInformation("DELETE STATUS:200");
        } catch (ParseException e) {
            Log.getInstance().addException("DELETE" + BAD_REQUEST);
            resp.sendError(400, "Bad Request");
        }
    }
}
