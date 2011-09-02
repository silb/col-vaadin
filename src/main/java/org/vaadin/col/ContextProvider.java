package org.vaadin.col;

/**
 * An interface for objects that are able to provide contexts.
 */
public interface ContextProvider {

    /**
     * Retrieve a context of a given type.
     *
     * @param <T> the type of the context
     * @param type the context class
     * @return the context or null if this object doesn't provide a context of the given type
     */
    public <T> T getContext(Class<T> type);

}
