package se.cronsioe.johan.rest;

import se.cronsioe.johan.api.Book;
import se.cronsioe.johan.api.BookService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;


@Path("/books")
public class RestBookService {

    private final BookService bookService;

    public RestBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response add(BookDTO book) {
        bookService.add(book);
        return Response.ok().build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getBooks(@QueryParam("author") String author) {

        Collection<Book> books = bookService.getBooks();

        if (author != null)
        {
            books = filterAuthor(author, books);
        }

        return Response.ok().entity(BookCollectionDTO.toDTO(books)).build();
    }

    private Collection<Book> filterAuthor(@QueryParam("author") String author, Collection<Book> books) {
        Collection<Book> filtered = new ArrayList<Book>();
        for (Book book : books)
        {
            if (author.equals(book.getAuthor()))
            {
                filtered.add(book);
            }
        }
        return filtered;
    }
}
