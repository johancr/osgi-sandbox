package se.cronsioe.johan.vaadin.osgi.bridge;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;
import se.cronsioe.johan.vaadin.osgi.bridge.spi.ApplicationFactory;

public class ApplicationFactoryTracker extends ServiceTracker {

    private final HttpService httpService;

    public ApplicationFactoryTracker(BundleContext context, HttpService httpService) {
        super(context, ApplicationFactory.class.getName(), null);
        this.httpService = httpService;
    }

    @Override
    public Object addingService(ServiceReference serviceReference) {
        String alias = (String) serviceReference.getProperty(ApplicationFactory.ALIAS);
        ApplicationFactory applicationFactory = (ApplicationFactory) context.getService(serviceReference);
        ApplicationFactoryServlet applicationFactoryServlet = new ApplicationFactoryServlet(applicationFactory);
        BundleHttpContext httpContext = new BundleHttpContext(serviceReference.getBundle());

        try
        {
            httpService.registerServlet("/" + alias, applicationFactoryServlet, null, httpContext);
            return alias;
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void removedService(ServiceReference serviceReference, Object service) {
        String alias = (String) service;
        httpService.unregister("/" + alias);
        context.ungetService(serviceReference);
    }
}
