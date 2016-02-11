import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;

/**
 * Created by djadjka_by on 09.02.2016.
 */
public class Message {
    private String id;
    private String author;
    private long timestamp;
    private String message;

    public Message(String message, String author, String id, long timestamp) {
        this.id = new String(id);
        this.message = new String(message);
        this.author = new String(author);
        this.timestamp = timestamp;
    }

    public Message(String author, String message) {
        this.id = new String(generateId());
        this.message = new String(message);
        this.author = new String(author);
        this.timestamp = generateTimestamp();
    }

    private long generateTimestamp() {
        Date d = new Date();
        return d.getTime();
    }

    private String generateId() {
        UUID idOne = UUID.randomUUID();
        return idOne.toString();
    }


    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
