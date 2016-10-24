package se.cronsioe.johan.rest;

import se.cronsioe.johan.api.Book;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "book")
public class BookDTO implements Book {

    private String author;
    private String title;

    public static BookDTO toDTO(Book book)
    {
        BookDTO dto = new BookDTO();
        dto.author = book.getAuthor();
        dto.title = book.getTitle();
        return dto;
    }

    public BookDTO()
    {
    }

    @Override
    public String getAuthor()
    {
        return author;
    } 

    @Override
    public String getTitle()
    {
        return title;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
