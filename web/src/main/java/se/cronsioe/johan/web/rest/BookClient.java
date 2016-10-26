package se.cronsioe.johan.web.rest;

import se.cronsioe.johan.api.Book;

import java.util.Collection;


public interface BookClient {

    void add(Book book);

    Collection<Book> getBooks();
}
