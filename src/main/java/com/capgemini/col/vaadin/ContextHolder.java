package com.capgemini.col.vaadin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * For holding serializable contexts. Components can contain an instance of this class and
 * provide a a delegate method to {@link #getContext(Class)} in order to implement {@link ContextProvider}.
 */
public class ContextHolder implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<String, Serializable> store = new HashMap<String, Serializable>();

    public <T> T getContext(Class<T> type) {
        Object context = store.get(type.getName());
        if (context != null && type.isAssignableFrom(context.getClass())) {
            return type.cast(context);
        }
        return null;
    }

    /**
     * Add or replace a context. The context will be registered under the name
     * of the given type. This method makes it possible to register a context
     * under a class that isn't serializable.
     *
     * @param <T> the type of the context.
     */
    public <T extends Serializable> void add(Class<? super T> type, T context) {
        store.put(type.getName(), context);
    }

    /**
     * Add or replace a context. The context will be registered under the name
     * of the context's class.
     */
    public <T extends Serializable> void add(T context) {
        store.put(context.getClass().getName(), context);
    }

}
