package by.bsu.chat;
import java.io.Serializable;

public class Message implements Serializable {

    private String id;
    private String author;
    private String text;
    private String method;
    private String photoURL = "";

    public Message() {
        this.author = "";
        this.text = "";
        this.photoURL = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", text='" + text + '\'' + ", " +
                "method='" + method + '\'' + ", " +
                ", photoURL='" + photoURL + '\'' +
                '}';
    }
}
