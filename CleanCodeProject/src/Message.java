import java.util.Date;
import java.util.UUID;

public class Message implements Comparable<Message> {
    private String id;
    private String author;
    private long timestamp;
    private String message;

    public Message(String message, String author, String id, long timestamp) {
        this.id = id;
        this.message = message;
        this.author = author;
        this.timestamp = timestamp;
    }

    public Message(String author, String message) {
        this.id = generateId();
        this.message = message;
        this.author = author;
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



    @Override
    public int compareTo(Message o) {
        return (int) (this.getTimestamp() - o.getTimestamp());
    }
}
