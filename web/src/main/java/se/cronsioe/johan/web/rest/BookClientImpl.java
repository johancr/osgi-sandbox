package se.cronsioe.johan.web.rest;

import se.cronsioe.johan.api.Book;
import se.cronsioe.johan.rest.BookCollectionDTO;
import se.cronsioe.johan.rest.BookDTO;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;

public class BookClientImpl implements BookClient {

    private final Client client = ClientBuilder.newClient();

    @Override
    public void add(Book book) {
        client.target("http://localhost:8080/services/books")
                .request().buildPost(Entity.json(toDTO(book))).invoke();
    }

    private static BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setAuthor(book.getAuthor());
        dto.setTitle(book.getTitle());
        return dto;
    }

    @Override
    public Collection<Book> getBooks() {
        BookCollectionDTO books = client.target("http://localhost:8080/services/books")
                .request(MediaType.APPLICATION_JSON).buildGet().invoke(BookCollectionDTO.class);

        return fromDTO(books);
    }

    private static Collection<Book> fromDTO(BookCollectionDTO dtos) {
        Collection<Book> books = new ArrayList<Book>();
        for (BookDTO dto : dtos.getBooks())
        {
            books.add(dto);
        }
        return books;
    }
}
