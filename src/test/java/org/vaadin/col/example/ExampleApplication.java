package org.vaadin.col.example;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.ServletHolder;
import org.vaadin.col.Context;
import org.vaadin.col.ContextHolder;
import org.vaadin.col.ContextLocator;
import org.vaadin.col.ContextProvider;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.ApplicationServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class ExampleApplication extends Application implements ContextProvider {
    private static final long serialVersionUID = 1L;

    private Window window;

    private ContextHolder ch = new ContextHolder();

    @Override
    public void init() {
        window = new Window("My Vaadin Application");
        setMainWindow(window);

        ch.add("Ehlo");

        final Button button = new Button("Click Me");
        button.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {
                ContextLocator locator = Context.locator(button);
                window.addComponent(new Label("Found context for Number.class: " + locator.locate(Number.class)));
                window.addComponent(new Label("Found context for String.class: " + locator.locate(String.class)));
                window.addComponent(new Label("Found context for Component.class: " + locator.locate(Component.class)));
                window.addComponent(new Label("Found context for Application.class: " + locator.locate(Application.class)));
            }
        });
        window.addComponent(button);

    }

    @Override
    public <T> T getContext(Class<T> type) {
        if (Number.class.isAssignableFrom(type)) {
            return type.cast(Integer.valueOf(1));
        }
        return ch.getContext(type);
    }

    /**
     * Start Jetty with this application at http://localhost:8080
     */
    public static void main(String[] args) throws Exception {
            Server server = new Server(8080);

            org.mortbay.jetty.servlet.Context root = new org.mortbay.jetty.servlet.Context(server, "/",
                    org.mortbay.jetty.servlet.Context.SESSIONS);
            ServletHolder sh = new ServletHolder(ApplicationServlet.class);
            sh.setInitParameter("application", ExampleApplication.class.getName());
            root.addServlet(sh, "/*");

            server.setStopAtShutdown(true);
            server.start();
    }
}
