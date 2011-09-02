package org.vaadin.col;

/**
 * A JVM wide configuration class. To use a custom implementation set the system
 * property <tt>org.vaadin.col.ContextConfig</tt> to the class name of the
 * implementation.
 * <p>
 * Example:
 * <pre>-Dorg.vaadin.col.ContextConfig=org.example.MyContextConfig</pre>
 */
public interface ContextConfig {

    /**
     * Indicates whether a context of a given type should always be retrieved
     * directly from the root.
     * <p/>
     * This method must be thread safe.
     */
    boolean fetchFromRoot(Class<?> type);

}
