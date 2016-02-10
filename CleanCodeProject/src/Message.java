import java.util.regex.Matcher;

/**
 * Created by djadjka_by on 09.02.2016.
 */
public class Message {
    private String id;
    private String author;
    private long timestamp;
    private String message;

    public Message(String id, String message, String author, long timestamp) {
        this.id = new String(id);
        this.message = new String(message);
        this.author = new String(author);
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }
    public String getMessage(){
        return message;
    }
    public long getTimestamp(){
        return timestamp;
    }
}
