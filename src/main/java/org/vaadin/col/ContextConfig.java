package org.vaadin.col;

public interface ContextConfig {

    /**
     * Indicates whether a context of a given type should always be retrieved
     * directly from the root.
     * <p/>
     * This method must be thread safe.
     */
    boolean fetchFromRoot(Class<?> type);

}
