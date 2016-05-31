package by.bsu.chat;

public interface Constants {
    String PROTOCOL = "http";
    String CONTEXT_PATH = "/chat";

    String REQUEST_METHOD_GET = "GET";
    String REQUEST_METHOD_POST = "POST";
    String REQUEST_METHOD_PUT = "PUT";
    String REQUEST_METHOD_DELETE = "DELETE";
    String REQUEST_METHOD_OPTIONS = "OPTIONS";

    String REQUEST_HEADER_ACCESS_CONTROL_ORIGIN = "Access-Control-Allow-Origin";
    String REQUEST_HEADER_ACCESS_CONTROL_METHODS = "Access-Control-Allow-Methods";


    int RESPONSE_CODE_OK = 200;
    int RESPONSE_CODE_NOT_MODIFIED = 200;
    int RESPONSE_CODE_BAD_REQUEST = 400;
    int RESPONSE_CODE_METHOD_NOT_ALLOWED = 405;
    int RESPONSE_CODE_INTERNAL_SERVER_ERROR = 500;
    int RESPONSE_CODE_NOT_IMPLEMENTED = 501;

    String REQUEST_PARAMS_DELIMITER = "&";
    String REQUEST_PARAM_TOKEN = "token";
    String REQUEST_PARAM_MESSAGE_ID = "msgId";
    String REGEX = "[{][^{]+[}]";
    String FILE_NAME = "H:\\messages.txt";
    int MESSAGE_FLUSH_TARIGGER = 3;

    static final String DB_CONNECTION ="jdbc:mysql://127.0.0.1:3306/chat?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static final String SQL_INSERT = "insert into messages (id,method,author,text) values (?,?,?,?);";
    static final String SQL_SELECT = "SELECT * FROM chat.messages limit ?,? ;";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "garena97";
    static final int ID_POSITION = 1;
    static final int METHOD_POSITION = 2;
    static final int AUTHOR_POSITION = 3;
    static final int TEXT_POSITION = 4;
    static final int FROM_POSITION = 1;
    static final int TO_POSITION = 1;

    interface Message {
        String FIELD_ID = "id";
        String FIELD_AUTHOR = "author";
        String FIELD_TEXT = "text";
        String FIELD_METHOD = "method";
        String FIELD_PHOTOURL = "photoURL";
    }
}
