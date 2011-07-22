package com.capgemini.col.vaadin;

import static org.junit.Assert.*;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;


public class ContextLocatorTest {

    private TestApplication application;
    private TestComponent c1;
    private TestComponent c11;
    private TestComponent c12;
    private TestComponent c111;
    private TestComponent c112;
    private TestComponent c121;

    static {
        System.setProperty(ContextConfig.class.getName(), TestContextConfig.class.getName());
    }

    @Before
    public void createComponents() {
        c1 = new TestComponent();
        c11 = new TestComponent();
        c12 = new TestComponent();
        c111 = new TestComponent();
        c112 = new TestComponent();
        c121 = new TestComponent();

        c1.addComponent(c11);
        c1.addComponent(c12);

        c11.addComponent(c111);
        c11.addComponent(c112);
        c12.addComponent(c121);

        application = new TestApplication(c1);
    }

    @Test
    public void sourceObjectAsContextWhenInstanceOfType() {
        Integer sourceObject = 1;
        Number context = Context.extract(sourceObject, Number.class);
        assertSame(sourceObject, context);
    }

    @Test
    public void contextRetrievalIsBottomUp() {
        c111.contextHolder.add(Integer.valueOf(42));
        c11.contextHolder.add(Integer.valueOf(41));
        application.contextHolder.add(Integer.valueOf(40));

        assertEquals(Integer.valueOf(42), Context.locate(c111, Integer.class));
        assertEquals(Integer.valueOf(41), Context.locate(c112, Integer.class));
        assertEquals(Integer.valueOf(40), Context.locate(c1, Integer.class));
    }

    @Test
    public void componentTreeIsOnlyTraversedOnce() {
        application.contextHolder.add(Integer.valueOf(42));
        c1.maxTraversal(1);
        c11.maxTraversal(1);
        Context.locate(c11, Integer.class);
    }

    @Test
    public void contextProviderTakesPrecedenceOverComponentImplemenation() {
        Serializable providedContext = "Serializable object through ContextProvider";
        c121.contextHolder.add(Serializable.class, providedContext);
        Object found = Context.locate(c121, Serializable.class);
        assertTrue(c121 instanceof Serializable);
        assertEquals(providedContext, found);
    }

    @Test
    public void emptyContextProviderRevertsToDirectImplementation() {
        Object found = Context.locate(c121, Serializable.class);
        assertEquals(c121, found);
    }

    @Test
    public void rootContextAlwaysRetrievedFromRoot() {
        TestRootContext context1 = new TestRootContext();
        TestRootContext context2 = new TestRootContext();

        c1.contextHolder.add(context2);
        application.contextHolder.add(context1);

        assertSame(context1, Context.locate(c111, TestRootContext.class));
    }
}
