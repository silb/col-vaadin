package com.capgemini.col.vaadin;

public interface ContextProvider {

    public <T> T getContext(Class<T> type);

}
