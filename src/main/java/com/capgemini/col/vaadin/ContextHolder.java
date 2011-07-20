package com.capgemini.col.vaadin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ContextHolder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<Class<?>, Object> store = new HashMap<Class<?>, Object>();

    public <T> T get(Class<T> type) {
        Object context = store.get(type);
        return type.cast(context);
    }

    public <T extends Serializable> void put(Class<? super T> type, T context) {
        store.put(type, context);
    }

}
