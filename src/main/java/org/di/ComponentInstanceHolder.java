package org.di;

import java.util.HashMap;
import java.util.Map;

public class ComponentInstanceHolder {

    Map<Class<?>, Object> context = new HashMap<>();

    public void putComponent(Class<?> key, Object value) {
        context.put(key, value);
    }

    public <T> T getComponent(Class<T> key) {
        return (T) context.get(key);
    }

    public boolean hasComponent(Class<?> key) {
        return context.containsKey(key);
    }

}
