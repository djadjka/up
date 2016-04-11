package by.bsu.up.chat.common.models;

import java.io.Serializable;

public class Message implements Serializable {

    private String id;
    private String author;
    private String text;
    private boolean del;
    private boolean update;
    private String photoURL="";

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

    public boolean getDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    public boolean getUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", text='" + text + '\'' + ", " +
                "del='" + del + '\'' + ", " +
                "update='" + update + '\'' +
                ", photoURL='" + photoURL + '\'' +
                '}';
    }
}
