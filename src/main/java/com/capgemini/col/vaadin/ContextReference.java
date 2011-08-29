package com.capgemini.col.vaadin;

import java.io.Serializable;

import com.vaadin.ui.Component;

/**
 * A serializable reference to a non-serializable context. Used when a
 * serializable component needs to store a non-serializable context in a field.
 *
 * @param <T> the type of the context
 */
public class ContextReference<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Component container;
    private final Class<T> type;
    private transient T context;

    public ContextReference(Component container, Class<T> type) {
        this.container = container;
        this.type = type;
    }

    public T get() {
        if (context == null) reload(); // Reload after de-serialization
        return context;
    }

    private void reload() {
        context = ContextLocator.locate(container, type);
    }

}
