package se.cronsioe.johan.vaadin.osgi.bridge.spi;

import com.vaadin.Application;
import com.vaadin.Application.SystemMessages;

public interface ApplicationFactory {

    String ALIAS = "alias";

    Application create();

    String getApplicationCSSClassName();

    SystemMessages getSystemMessages();
}
