package se.cronsioe.johan.web.osgi;

import org.osgi.framework.Bundle;
import org.osgi.service.http.HttpContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

public class BundleHttpContext implements HttpContext {

    private final Bundle bundle;

    public BundleHttpContext(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public boolean handleSecurity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return true;
    }

    @Override
    public URL getResource(String name) {
        return bundle.getResource(name);
    }

    @Override
    public String getMimeType(String name) {
        return null;
    }
}
