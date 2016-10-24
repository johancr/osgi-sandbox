package se.cronsioe.johan.inmemory.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import se.cronsioe.johan.api.BookService;
import se.cronsioe.johan.inmemory.InMemoryBookService;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) {
        context.registerService(BookService.class.getName(), new InMemoryBookService(), null);
    }

    @Override
    public void stop(BundleContext context) {

    }
}
