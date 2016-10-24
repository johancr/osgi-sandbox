package se.cronsioe.johan.inmemory;

import se.cronsioe.johan.api.Book;
import se.cronsioe.johan.api.BookService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InMemoryBookService implements BookService {

    private final List<Book> books = new ArrayList<Book>();

    @Override
    public void add(Book book) {
        books.add(book);
    }

    @Override
    public Collection<Book> getBooks() {
        return books;
    }

}
