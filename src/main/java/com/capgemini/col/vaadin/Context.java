package com.capgemini.col.vaadin;

import com.vaadin.ui.Component;

/**
 * Serves as the entry point for the API. All functionality is available through
 * static methods.
 */
public class Context {

    static ContextConfig config;

    static {
        String configClassName = System.getProperty(ContextConfig.class.getName());
        if (configClassName != null) {
            try {
                Class<? extends ContextConfig> configClass = Class.forName(configClassName).asSubclass(ContextConfig.class);
                config = configClass.newInstance();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            config = new ContextConfig() {

                @Override
                public boolean fetchFromRoot(Class<?> type) {
                    return false;
                }

            };
        }

    }

    /**
     * Find a context from the given component.
     */
    public static ContextLocator from(Component component) {
        return new ContextLocator(component);
    }

    /**
     * Obtain a serializable reference to a possibly non-serializable context.
     */
    public static <T> ContextReference<T> ref(Component container, Class<T> type) {
        return new ContextReference<T>(container, type);
    }

    /**
     * Obtain a locator for locating multiple contexts from the same component.
     */
    public static ContextLocator locator(Component component) {
        return new ContextLocator(component);
    }

}
