package se.cronsioe.johan.vaadin.osgi.bridge;

import org.osgi.framework.*;

import static org.osgi.framework.Bundle.UNINSTALLED;
import static org.osgi.framework.Constants.EXPORT_PACKAGE;

public class Activator implements BundleActivator, BundleListener {

    private static final String VAADIN_PACKAGE = "com.vaadin";
    private HttpServiceTracker httpServiceTracker;
    private Bundle vaadinBundle;
    private BundleContext context;

    @Override
    public void start(BundleContext context) {
        this.context = context;

        for (Bundle bundle : context.getBundles())
        {
            if (isVaadin(bundle) && isNotUninstalled(bundle))
            {
                vaadinBundle = bundle;
                httpServiceTracker = new HttpServiceTracker(context, vaadinBundle);
                httpServiceTracker.open();
                break;
            }
        }
    }

    @Override
    public void stop(BundleContext context) {
        if (httpServiceTracker != null)
        {
            httpServiceTracker.close();
        }
    }

    public boolean isVaadin(Bundle bundle) {
        String exportPackage = bundle.getHeaders().get(EXPORT_PACKAGE);
        return exportPackage != null && exportPackage.contains(VAADIN_PACKAGE);
    }

    public boolean isNotUninstalled(Bundle bundle) {
        return bundle.getState() != UNINSTALLED;
    }

    @Override
    public void bundleChanged(BundleEvent event) {
        if (isVaadinBeingUninstalled(event))
        {
            httpServiceTracker.close();
        }
        else if (isVaadinBeingInstalled(event))
        {
            vaadinBundle = event.getBundle();
            httpServiceTracker = new HttpServiceTracker(context, vaadinBundle);
            httpServiceTracker.open();
        }
    }

    private boolean isVaadinBeingUninstalled(BundleEvent event) {
        return event.getType() == UNINSTALLED && event.getBundle() == vaadinBundle;
    }

    private boolean isVaadinBeingInstalled(BundleEvent event) {
        return event.getType() != UNINSTALLED && vaadinBundle == null &&
                isVaadin(event.getBundle());
    }
}
