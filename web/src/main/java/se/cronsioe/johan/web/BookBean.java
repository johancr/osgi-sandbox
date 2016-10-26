package se.cronsioe.johan.web;

import se.cronsioe.johan.api.Book;

public class BookBean implements Book {

    private String author = "";
    private String title = "";

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
