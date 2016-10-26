package se.cronsioe.johan.web.component;

import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import se.cronsioe.johan.api.Book;
import se.cronsioe.johan.web.rest.BookClient;

public class ShowBooksComponent extends Table implements Component.Listener {

    private final BookClient client;

    public ShowBooksComponent(BookClient client) {
        this.client = client;

        addContainerProperty("Author", String.class, null);
        addContainerProperty("Title", String.class, null);

        reloadTable();
    }

    private void reloadTable() {
        removeAllItems();
        for (Book book : client.getBooks())
        {
            addItem(new Object[]{book.getAuthor(), book.getTitle()}, null);
        }
    }

    @Override
    public void componentEvent(Event event) {
        reloadTable();
    }
}
