package org.vaadin.col;

public class TestContextConfig implements ContextConfig {

    @Override
    public boolean fetchFromRoot(Class<?> type) {
        return TestRootContext.class.equals(type);
    }

}
