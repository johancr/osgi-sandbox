package se.cronsioe.johan.console;

import org.apache.felix.service.command.CommandProcessor;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import se.cronsioe.johan.spi.ServiceTrackedBookService;

import java.util.Dictionary;
import java.util.Hashtable;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) {
        BookConsole bookConsole = new BookConsole(ServiceTrackedBookService.from(context));

        Dictionary<String, Object> properties = new Hashtable<String, Object>();
        properties.put(CommandProcessor.COMMAND_SCOPE, "book");
        properties.put(CommandProcessor.COMMAND_FUNCTION, new String[]{"add", "print"});

        context.registerService(BookConsole.class.getName(), bookConsole, properties);
    }

    @Override
    public void stop(BundleContext context) {

    }
}
