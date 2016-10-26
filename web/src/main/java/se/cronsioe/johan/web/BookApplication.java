package se.cronsioe.johan.web;

import com.vaadin.Application;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Window;
import se.cronsioe.johan.web.component.AddBookComponent;
import se.cronsioe.johan.web.component.ShowBooksComponent;
import se.cronsioe.johan.web.rest.BookClient;
import se.cronsioe.johan.web.rest.BookClientImpl;

public class BookApplication extends Application {

    private final BookClient bookClient = new BookClientImpl();

    @Override
    public void init() {
        setTheme("web");
        HorizontalLayout main = new HorizontalLayout();

        ShowBooksComponent showBooksComponent = new ShowBooksComponent(bookClient);
        AddBookComponent addBookComponent = new AddBookComponent(bookClient);
        addBookComponent.addListener((Component.Listener) showBooksComponent);

        main.addComponent(showBooksComponent);
        main.addComponent(addBookComponent);

        setMainWindow(new Window("Book application", main));
    }
}
