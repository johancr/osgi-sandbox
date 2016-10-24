package se.cronsioe.johan.rest.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import se.cronsioe.johan.api.Book;
import se.cronsioe.johan.api.BookService;
import se.cronsioe.johan.rest.RestBookService;
import se.cronsioe.johan.spi.ServiceTrackedBookService;

import java.util.Collection;
import java.util.Collections;


public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) {
        RestBookService restBookService = new RestBookService(ServiceTrackedBookService.from(context));
        context.registerService(Object.class.getName(), restBookService, null);
    }

    @Override
    public void stop(BundleContext context) {

    }
}
