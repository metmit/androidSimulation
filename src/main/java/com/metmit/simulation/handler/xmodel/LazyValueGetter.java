package com.metmit.simulation.handler.xmodel;

import com.metmit.simulation.handler.ViewImage;

public class LazyValueGetter<T> {
    private ValueGetter<T> delegate;
    private T theValue;
    private boolean hasDelegateCalled = false;
    private ViewImage viewImage;

    LazyValueGetter(ValueGetter<T> delegate, ViewImage viewImage) {
        this.delegate = delegate;
        this.viewImage = viewImage;
    }


    public boolean support(Class type) {
        return delegate.support(type);
    }

    public T get() {
        if (hasDelegateCalled) {
            return theValue;
        }
        synchronized (this) {
            if (hasDelegateCalled) {
                return theValue;
            }
            theValue = delegate.get(viewImage);
            hasDelegateCalled = true;
        }
        return theValue;
    }

    public String attr() {
        return delegate.attr();
    }
}
