package se.cronsioe.johan.console;

import se.cronsioe.johan.api.Book;
import se.cronsioe.johan.api.BookService;

public class BookConsole {

    private final BookService bookService;

    public BookConsole(BookService bookService)
    {
        this.bookService = bookService;
    }

    public void print()
    {
        for(Book book : bookService.getBooks())
        {
            System.out.println(book.getTitle() + " by " + book.getAuthor());
        }
    }

    public void add(final String title, final String author)
    {
        bookService.add(new Book() {

            public String getTitle() {
                return title;
            }

            public String getAuthor() {
                return author;
            }
        });
    }
}

