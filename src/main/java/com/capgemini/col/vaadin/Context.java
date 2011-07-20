package com.capgemini.col.vaadin;

import com.vaadin.ui.Component;

public class Context {

    public static <T> T locate(Component component, Class<T> type) {
        Component current = component;

        while(current != null) {
            T ctx = getContext(current, type);
            if (ctx != null) return ctx;
            Component next = current.getParent();
            if (next != null) current = next;
            else return getContext(current.getApplication(), type);
        }

        return null;

    }

    private static <T> T getContext(Object container, Class<T> type) {

        if (type.isAssignableFrom(container.getClass())) {
            return type.cast(container);
        } else if (container instanceof ContextProvider) {
            return ((ContextProvider) container).getContext(type);
        }

        return null;
    }

}
