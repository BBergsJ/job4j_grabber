package ru.job4j.grabber;

public class Post {

    private String url;
    private String title;
    private String text;
    private String date;
    private int id;

    public Post(String url, String title, String text, String date) {
        this.url = url;
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public Post() {
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Post{"
                + "url='" + url + '\''
                + ", title='" + title + '\''
                + ", text='" + text + '\''
                + ", date='" + date + '\''
                + ", id=" + id
                + '}';
    }
}
