package se.cronsioe.johan.web.osgi;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.osgi.util.tracker.ServiceTracker;

public class HttpServiceTracker extends ServiceTracker<HttpService, HttpService> {

    public HttpServiceTracker(BundleContext context) {
        super(context, HttpService.class.getName(), null);
    }

    @Override
    public HttpService addingService(ServiceReference reference) {
        HttpService httpService = (HttpService) context.getService(reference);
        try
        {
            HttpContext httpContext = new BundleHttpContext(context.getBundle());
            httpService.registerResources("/VAADIN/themes/web", "VAADIN/themes/web", httpContext);
        }
        catch (NamespaceException ex)
        {
            throw new IllegalStateException("Could not register Vaadin resources" + ex, ex);
        }

        return httpService;
    }

    @Override
    public void removedService(ServiceReference serviceReference, HttpService service) {
        context.ungetService(serviceReference);
    }
}
