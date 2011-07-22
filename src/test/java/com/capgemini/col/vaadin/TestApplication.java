package com.capgemini.col.vaadin;

import com.vaadin.Application;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public class TestApplication extends Application implements ContextProvider {

    private static final long serialVersionUID = 1L;

    public ContextHolder contextHolder = new ContextHolder();

    Window window = new Window();

    public TestApplication(Component root) {
        super();
        window.addComponent(root);
        setMainWindow(window);
    }

    @Override
    public void init() {

    }

    @Override
    public <T> T getContext(Class<T> type) {
        return contextHolder.getContext(type);
    }

}
