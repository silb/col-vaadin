package com.capgemini.col.vaadin;

public class TestContextConfig implements ContextConfig {

    @Override
    public boolean fetchFromRoot(Class<?> type) {
        return TestRootContext.class.equals(type);
    }

}
