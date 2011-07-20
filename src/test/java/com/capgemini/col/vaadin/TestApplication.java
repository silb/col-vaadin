package com.capgemini.col.vaadin;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class TestApplication extends Application implements ContextProvider {
    private static final long serialVersionUID = 1L;

    private Window window;

    private ContextHolder ch = new ContextHolder();

    @Override
    public void init() {
        window = new Window("My Vaadin Application");
        setMainWindow(window);

        ch.put(String.class, "Ehlo");

        final Button button = new Button("Click Me");
        button.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            public void buttonClick(ClickEvent event) {
                window.addComponent(new Label("Found context for Number.class: " + Context.locate(button, Number.class)));
                window.addComponent(new Label("Found context for String.class: " + Context.locate(button, String.class)));
            }
        });
        window.addComponent(button);

    }

    public <T> T getContext(Class<T> type) {
        if (Number.class.isAssignableFrom(type)) {
            return type.cast(Integer.valueOf(1));
        }
        return ch.get(type);
    }

    public static void main(String[] args) throws Exception {
            Server server = new Server(8080);
            WebAppContext context = new WebAppContext(server, "src/main/webapp", "/");
            context.setParentLoaderPriority(true);
            server.setStopAtShutdown(true);
            server.start();
    }
}
