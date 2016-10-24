package se.cronsioe.johan.vaadin.osgi.bridge;

import com.vaadin.Application;
import com.vaadin.Application.SystemMessages;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;
import se.cronsioe.johan.vaadin.osgi.bridge.spi.ApplicationFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class ApplicationFactoryServlet extends AbstractApplicationServlet {

    private final ApplicationFactory applicationFactory;

    public ApplicationFactoryServlet(ApplicationFactory applicationFactory) {
        this.applicationFactory = applicationFactory;
    }

    @Override
    protected Application getNewApplication(HttpServletRequest request) throws ServletException {
        return applicationFactory.create();
    }

    @Override
    protected Class<? extends Application> getApplicationClass() {
        return null;
    }

    @Override
    protected String getApplicationCSSClassName() {
        return applicationFactory.getApplicationCSSClassName();
    }

    @Override
    protected SystemMessages getSystemMessages() {
        SystemMessages messages = applicationFactory.getSystemMessages();
        return messages != null ? messages : Application.getSystemMessages();
    }
}
