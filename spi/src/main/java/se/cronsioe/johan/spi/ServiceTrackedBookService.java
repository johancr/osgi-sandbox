package se.cronsioe.johan.spi;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import se.cronsioe.johan.api.Book;
import se.cronsioe.johan.api.BookService;

import java.util.Collection;
import java.util.Collections;

public class ServiceTrackedBookService implements BookService {

    private final ServiceTracker<BookService, BookService> serviceTracker;

    private ServiceTrackedBookService(ServiceTracker<BookService, BookService> serviceTracker) {
        this.serviceTracker = serviceTracker;
    }

    public static BookService from(ServiceTracker<BookService, BookService> serviceTracker) {
       return new ServiceTrackedBookService(serviceTracker);
    }

    public static BookService from(BundleContext context) {
        ServiceTracker<BookService, BookService> serviceTracker = new ServiceTracker<BookService, BookService>(context, BookService.class.getName(), null);
        serviceTracker.open();
        return from(serviceTracker);
    }

    @Override
    public void add(Book book) {
        BookService service = serviceTracker.getService();

        if (service != null)
        {
            service.add(book);
        }
        else
        {
            System.out.println("No book service available");
        }
    }

    @Override
    public Collection<Book> getBooks() {
        BookService service = serviceTracker.getService();

        if (service != null)
        {
            return service.getBooks();
        }
        else
        {
            System.out.println("No book service available");
            return Collections.emptyList();
        }
    }
}
