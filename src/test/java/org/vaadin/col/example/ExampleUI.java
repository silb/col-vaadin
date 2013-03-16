package org.vaadin.col.example;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.ServletHolder;
import org.vaadin.col.Context;
import org.vaadin.col.ContextHolder;
import org.vaadin.col.ContextLocator;
import org.vaadin.col.ContextProvider;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ExampleUI extends UI implements ContextProvider {
    private static final long serialVersionUID = 1L;

    private ContextHolder ch = new ContextHolder();

    private VerticalLayout view;

    @Override
    public void init(VaadinRequest request) {
        view = new VerticalLayout();

        ch.add("Ehlo");

        final Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {
                ContextLocator locator = Context.locator(button);
                view.addComponent(new Label("Found context for Number.class: " + locator.locate(Number.class)));
                view.addComponent(new Label("Found context for String.class: " + locator.locate(String.class)));
                view.addComponent(new Label("Found context for Component.class: " + locator.locate(Component.class)));
                view.addComponent(new Label("Found context for UI.class: " + locator.locate(UI.class)));
            }
        });
        view.addComponent(button);
        setContent(view);
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
            ServletHolder sh = new ServletHolder(com.vaadin.server.VaadinServlet.class);
            sh.setInitParameter("UI", ExampleUI.class.getName());
            root.addServlet(sh, "/*");

            server.setStopAtShutdown(true);
            server.start();
    }
}
