package se.cronsioe.johan.web.component;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Form;
import com.vaadin.ui.TextField;
import se.cronsioe.johan.web.BookBean;
import se.cronsioe.johan.web.rest.BookClient;

public class AddBookComponent extends Form {

    public AddBookComponent(BookClient client) {

        setCaption("Add a book");
        getLayout().setMargin(true);

        BookBean book = new BookBean();
        BeanItem<BookBean> item = new BeanItem<BookBean>(book);

        TextField author = new TextField("Author", item.getItemProperty("author"));
        getLayout().addComponent(author);

        TextField title = new TextField("Title", item.getItemProperty("title"));
        getLayout().addComponent(title);

        Button add = new Button("Add", add(book, client));
        getLayout().addComponent(add);
    }

    private Button.ClickListener add(final BookBean book, final BookClient client) {
        return new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                client.add(book);
                fireComponentEvent();
            }
        };
    }
}
