package org.vaadin.col;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;

public class TestComponent extends AbstractComponentContainer implements ContextProvider {

    private static final long serialVersionUID = 1L;

    ContextHolder contextHolder = new ContextHolder();

    private List<Component> children = new ArrayList<Component>();

    private int maxTraversal;
    private int traversalCount;

    @Override
    public HasComponents getParent() {
        if (maxTraversal > 0) {
            traversalCount += 1;

            if (traversalCount > maxTraversal)
                throw new AssertionError("The component has reached its max traversal limit " + maxTraversal);
        }

        return super.getParent();
    }

    @Override
    public void replaceComponent(Component oldComponent, Component newComponent) {
    }

    @Override
    public void addComponent(Component c) {
        super.addComponent(c);
        children.add(c);
    }

    @Override
    public void removeComponent(Component c) {
        super.removeComponent(c);
        children.remove(c);
    }

    @Override
    public Iterator<Component> iterator() {
        return children.iterator();
    }

    @Override
    public int getComponentCount() {
        return children.size();
    }

    @Override
    public <T> T getContext(Class<T> type) {
        return contextHolder.getContext(type);
    }

    public void maxTraversal(int maxTraversal) {
        this.maxTraversal = maxTraversal;
    }
}
