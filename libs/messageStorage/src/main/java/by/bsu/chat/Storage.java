package by.bsu.chat;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by djadjka.by on 28.05.16.
 */
public class Storage implements MessageStorage {

    private Connection dbConnection = null;
    private PreparedStatement psMesInsert = null;
    private PreparedStatement psMesSelect =null;
//    private DataSource ds = null;
//
//    private static DataSource getDataSource() {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setUrl(Constants.DB_CONNECTION);
//        dataSource.setUsername(Constants.DB_USER);
//        dataSource.setPassword(Constants.DB_PASSWORD);
//        return dataSource;
//    }


    public Storage() {
        //ds = getDataSource();
        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/chat?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "garena97");
            System.out.println("aaaaaaaa");
            psMesInsert = dbConnection.prepareStatement(Constants.SQL_INSERT);
            psMesSelect = dbConnection.prepareStatement(Constants.SQL_SELECT);

        } catch (SQLException e) {
            Log.getInstance().addException(e.getMessage());
        }
    }


    @Override
    public List<Message> getPortion(Portion portion) {

        int from = portion.getFromIndex();
        List<Message> messages = new ArrayList<>();
        if (from < 0) {
            throw new IllegalArgumentException(String.format("Portion from index %d can not be less then 0", from));
        }
        int to = portion.getToIndex();
        if (to != -1 && to < portion.getFromIndex()) {
            throw new IllegalArgumentException(String.format("Porting last index %d can not be less then start index %d", to, from));
        }
        try {
            psMesSelect.setString(Constants.FROM_POSITION, String.valueOf(from));
            psMesSelect.setString(Constants.TO_POSITION, String.valueOf(to));
            ResultSet rs = psMesSelect.executeQuery();
            while (rs.next()) {
                String id = rs.getString(Constants.Message.FIELD_ID);
                String method = rs.getString(Constants.Message.FIELD_METHOD);
                String author = rs.getString(Constants.Message.FIELD_ID);
                String text  = rs.getString(Constants.Message.FIELD_TEXT);
                Message message = new Message();
                message.setId(id);
                message.setMethod(method);
                message.setAuthor(author);
                message.setText(text);
                message.setPhotoURL("");
                messages.add(message);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }


        return messages;
    }

    @Override
    public void addMessage(Message message) {
        try {
            psMesInsert.setString(Constants.ID_POSITION, message.getId());
            psMesInsert.setString(Constants.METHOD_POSITION, message.getMethod());
            psMesInsert.setString(Constants.AUTHOR_POSITION, message.getAuthor());
            psMesInsert.setString(Constants.TEXT_POSITION, message.getText());
            psMesInsert.executeUpdate();
        }catch (SQLException e) {
            Log.getInstance().addException(e.getMessage());
        }

    }

    @Override
    public boolean updateMessage(Message message) {
        return false;
    }

    @Override
    public boolean removeMessage(String messageId) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }
}
