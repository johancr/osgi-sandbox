package se.cronsioe.johan.rest;

import se.cronsioe.johan.api.Book;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@XmlRootElement(name = "books")
public class BookCollectionDTO {

    private Collection<BookDTO> books;

    private BookCollectionDTO(Collection<BookDTO> books) {
        this.books = books;
    }

    public BookCollectionDTO()
    {
    }

    public static BookCollectionDTO toDTO(Collection<Book> books) {
        Collection<BookDTO> dto = new ArrayList<BookDTO>();
        for (Book book : books)
        {
            dto.add(BookDTO.toDTO(book));
        }
        return BookCollectionDTO.of(dto);
    }

    public static BookCollectionDTO of(Collection<BookDTO> books) {
        return new BookCollectionDTO(books);
    }

    @XmlElement(name = "books")
    public Collection<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }
}
