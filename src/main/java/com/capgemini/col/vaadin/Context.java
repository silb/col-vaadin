package com.capgemini.col.vaadin;

import com.vaadin.Application;
import com.vaadin.ui.Component;

public class Context {

    private static ContextConfig config;

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
     * Locate a context of a given type.
     *
     * The search algorithm is as follows:
     * <dl>
     * <dt> Through the component hierarchy (default)
     * <dd> The search traverses upwards from the given component to the topmost component and ends at the
     * application.
     * <dt> Directly from the root ({@linkplain Application application})
     * <dd> The context is retrieved directly from the root if the instance of {@link #config} returns true for {@link ContextConfig#fetchFromRoot(Class)} for <tt>type</tt>
     * </ul>
     * Components and applications are able to provide a context of a given type T if they either implement
     * ContextProvider and return it from {@link ContextProvider#getContext(Class)} or if they are an instance of
     * T themselves.
     *
     * @param <T> the type of the context
     * @param component the component where the lookup starts
     * @param type the class of the context type
     * @return the context or null if no context was found
     */
    public static <T> T locate(Component component, Class<T> type) {

        if (config.fetchFromRoot(type)) {
            return extractFromRoot(component, type);
        }

        return extractFromHierarchy(component, type);

    }

    static <T> T extract(Object container, Class<T> type) {

        if (container instanceof ContextProvider) {
            T context = ((ContextProvider) container).getContext(type);
            if (context != null) return context;
        }

        if (type.isAssignableFrom(container.getClass())) {
            return type.cast(container);
        }

        return null;
    }

    static <T> T extractFromRoot(Component container, Class<T> type) {
        if (container.getApplication() == null) {
            throw new IllegalStateException("Cannot locate context of type " + type.getName() + ": The component is not connected to an application.");
        }

        return extract(container.getApplication(), type);
    }

    static <T> T extractFromHierarchy(Component component, Class<T> type) {
        Component current = component;

        while(current != null) {
            T ctx = extract(current, type);
            if (ctx != null) return ctx;
            Component next = current.getParent();
            if (next != null) current = next;
            else return extractFromRoot(current, type);
        }

        return null;
    }

}
