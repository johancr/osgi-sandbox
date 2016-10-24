package se.cronsioe.johan.api;

import java.util.Collection;

public interface BookService {

    void add(Book book);

    Collection<Book> getBooks();
}
