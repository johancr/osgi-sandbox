package se.cronsioe.johan.web.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import se.cronsioe.johan.api.BookService;
import se.cronsioe.johan.vaadin.osgi.bridge.spi.ApplicationFactory;

import java.util.Dictionary;
import java.util.Hashtable;

public class Activator implements BundleActivator {

    private ServiceRegistration serviceRegistration;
    private HttpServiceTracker httpServiceTracker;

    @Override
    public void start(BundleContext context) {
        ServiceTracker<BookService, BookService> bookServiceTracker =
                new ServiceTracker<BookService, BookService>(context, BookService.class.getName(), null);
        bookServiceTracker.open();

        httpServiceTracker = new HttpServiceTracker(context);
        httpServiceTracker.open();

        Dictionary<String, Object> dict = new Hashtable<String, Object>();
        dict.put(ApplicationFactory.ALIAS, "web");

        serviceRegistration = context.registerService(ApplicationFactory.class.getName(),
                new BookApplicationFactory(), dict);
    }

    @Override
    public void stop(BundleContext context) {
        serviceRegistration.unregister();
    }
}
