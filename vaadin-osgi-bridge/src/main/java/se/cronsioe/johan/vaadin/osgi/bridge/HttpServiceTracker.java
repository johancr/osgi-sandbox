package se.cronsioe.johan.vaadin.osgi.bridge;


import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.osgi.util.tracker.ServiceTracker;


public class HttpServiceTracker extends ServiceTracker {

    private Bundle vaadinBundle;
    private ApplicationFactoryTracker applicationFactoryTracker;

    public HttpServiceTracker(BundleContext context, Bundle vaadinBundle) {
        super(context, HttpService.class.getName(), null);
        this.vaadinBundle = vaadinBundle;
    }

    @Override
    public Object addingService(ServiceReference reference) {
        HttpService httpService = (HttpService) context.getService(reference);
        try
        {
            HttpContext httpContext = new BundleHttpContext(vaadinBundle);
            httpService.registerResources("/VAADIN", "VAADIN", httpContext);

            applicationFactoryTracker = new ApplicationFactoryTracker(context, httpService);
            applicationFactoryTracker.open();
        }
        catch (NamespaceException ex)
        {
            throw new RuntimeException(ex);
        }

        return httpService;
    }

    @Override
    public void removedService(ServiceReference serviceReference, Object service) {
        applicationFactoryTracker.close();
        context.ungetService(serviceReference);
    }
}
