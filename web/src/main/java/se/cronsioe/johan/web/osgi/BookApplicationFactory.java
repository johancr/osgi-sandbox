package se.cronsioe.johan.web.osgi;

import com.vaadin.Application;
import com.vaadin.Application.SystemMessages;
import se.cronsioe.johan.vaadin.osgi.bridge.spi.ApplicationFactory;
import se.cronsioe.johan.web.BookApplication;

public class BookApplicationFactory implements ApplicationFactory {

    @Override
    public Application create() {
        return new BookApplication();
    }

    @Override
    public String getApplicationCSSClassName() {
        return getClass().getName();
    }

    @Override
    public SystemMessages getSystemMessages() {
        return null;
    }
}
