package org.vaadin.col;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class TestApplication extends UI implements ContextProvider {

    private static final long serialVersionUID = 1L;

    public ContextHolder contextHolder = new ContextHolder();

    VerticalLayout view = new VerticalLayout();

    public TestApplication(Component root) {
        super();
        view.addComponent(root);
        setContent(view);
    }

    @Override
    public void init(VaadinRequest request) {

    }

    @Override
    public <T> T getContext(Class<T> type) {
        return contextHolder.getContext(type);
    }

}
