# Contextual Object Lookup (COL) for Vaadin

*Inter-component communication and service layer integration for Vaadin*

This library provides a mechanism for decoupling Vaadin components at different
levels of the component hierarchy from each other.

The typical scenarios where COL is used is as follows:

 1. A component deep down in the hierarchy needs to communicate with some other
    component closer to the root of the hierarchy.
 2. A component deep down in the hierarchy needs to operate on an object that is
    provided by some other component closer to the root of the hierarchy.
 3. A component needs access to the service layer.

The decoupling is provided by an intermediary lookup mechanism which traverses
the component hierarchy. As stated above, it can be used for retrieving:

 1. Components further up in the hierarchy
 2. Objects that components up in the hierarchy provide to other components
 3. Services

# Example usage

## Providing objects to child components

```java
public ParentComponent extends AbstractComponent implements ContextProvider {
    private ContextHolder ch = new ContextHolder();

    public ParentComponent() {
        ch.add(new Customer());
    }

    public <T> T getContext(Class<T> type) {
        return ch.getContext(type);
    }
}

public ChildButton extends Button implements Button.ClickListener {
    public ChildButton() {
        super(this);
    }

    public void buttonClick(ClickEvent event) {
        Customer customer = Context.from(this).locate(Customer.class);
        customer.doSomething();
    }
}
```

## Providing Spring services as contexts

```java
public MyApplication extends Application implements ContextProvider {
  public <T> T getContext(Class<T> type) {
      ServletContext sc = ((WebApplicationContext) getContext()).getHttpSession().getServletContext();
      return WebApplicationContextUtils.getRequiredWebApplicationContext(sc).getBean(type);
  }
}

public MyComponent extends AbstractComponent {
  public void attach() {
    // Note: The context cannot be retrieved in the constructor as the component
    // has yet to be attached to the component tree.
    MySpringService mySpringService = Context.from(this).locate(MySpringService.class);
  }
}
```

# The Contextual Object Lookup (COL) Pattern

The pattern is somewhat similar to the Service Locator (SL) pattern in that they
are both used for locating objects (or services). COL differs from SL as the
lookup may return different results depending on the position in the component
tree where it starts.

Example component tree:
```
        R (Provides: CustomerService, Customer)
       / \
   C1 -   - C2 (Provides: Customer)
          \
           - C21
            \
             - C211
```

The component C1 is used for editing the name of a customer. The customer whose
name is to be edited is obtained through COL starting from C1 itself. As C1 does
not implement Customer nor provide Customer through ContextProvider, the COL
continues up to R, the root component (a Vaadin Application). R provides a
Customer which is then returned to C1 as the result of the COL. C1 will
therefore be editing the name of the customer provided by R.

In order to store a customer, C1 needs access to a CustomerService. C1 will use
COL to obtain the service. The search for CustomerService ends up in the root
component R which will query the service layer for CustomerService.

The component C211 displays a summary of a customer. In order to obtain the
customer it performs COL starting from itself. C211 and C21 do not implement
Customer nor do they provide a customer through ContextProvider. The COL
therefore continues up to C2 which does provide a customer. Note that this
customer is different from the customer that is provided by R. The COL
terminates and returns the customer from C2 to C211. By using COL for looking up
the customer we avoid having any dependency or object reference from C211 to C2.
This gets increasingly important as the distance between C211 and C2
increases.
