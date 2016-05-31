import by.bsu.chat.Message;
import by.bsu.chat.MessageStorage;
import by.bsu.chat.Storage;

import java.sql.*;
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Message m = new Message();
        m.setId("asdasda");
        m.setAuthor("djadjka");
        m.setText("dasdadsd");
        MessageStorage ms = new Storage();
        ms.addMessage(m);
    }
}
