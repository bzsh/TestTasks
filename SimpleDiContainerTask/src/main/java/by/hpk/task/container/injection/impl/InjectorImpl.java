package by.hpk.task.container.injection.impl;

import by.hpk.task.container.annotation.Inject;
import by.hpk.task.container.exception.BindingNotFoundException;
import by.hpk.task.container.exception.ConstructorNotFoundException;
import by.hpk.task.container.exception.TooManyConstructorsException;
import by.hpk.task.container.injection.Injector;
import by.hpk.task.container.providing.Provider;
import by.hpk.task.container.providing.ProviderImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class InjectorImpl implements Injector {

    private final Map<Class<?>, Class<?>> prototypeClasses = new HashMap<>();
    private final Map<Class<?>, Class<?>> singletonClasses = new HashMap<>();
    private final Map<Class<?>, Object> singletonInstances = new HashMap<>();

    @Override
    public <T> Provider<T> getProvider(Class<T> type) {
        if (prototypeClasses.get(type) != null) {
            return getPrototypeProvider(type);
        } else if (singletonClasses.get(type) != null) {
            return getSingletonProvider(type);
        }
        return null;
    }

    @Override
    public <T> void bind(Class<T> intf, Class<? extends T> impl) {
        if (hasMoreThanOneInjectConstructors(impl)) {
            throw new TooManyConstructorsException("Too many constructors with @Inject");
        }
        prototypeClasses.put(intf, impl);
    }

    @Override
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {
        if (hasMoreThanOneInjectConstructors(impl)) {
            throw new TooManyConstructorsException("Too many constructors with @Inject");
        }
        singletonClasses.put(intf, impl);
    }

    private <T> Provider<T> getSingletonProvider(Class<T> type) {
        Object singleton = singletonInstances.get(type);
        if (singleton != null) {
            return new ProviderImpl<>((T) singleton);
        }
        Class<?> impl = prototypeClasses.get(type);
        singleton = getInstance(impl);
        singletonInstances.put(type, singleton);
        return new ProviderImpl<>((T) singleton);
    }

    private <T> Provider<T> getPrototypeProvider(Class<T> type) {
        Class<?> impl = prototypeClasses.get(type);
        return new ProviderImpl<>((T) getInstance(impl));
    }

    private <T> Object getInstance(Class<T> impl) {
        Object result;
        if (hasConstructorWithInject(impl)) {
            result = getInstanceWithArgs(impl);
        } else {
            result = getInstanceWithNoArgs(impl);
        }
        return result;
    }

    private <T> Object getInstanceWithNoArgs(Class<T> impl) {
        Object result = null;
        Constructor<?> constructor = getDefaultConstructor(impl);
        try {
            result = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    private <T> Object getInstanceWithArgs(Class<T> impl) {
        Constructor<?> constructor = getConstructorWithInject(impl);
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] instances = new Object[paramTypes.length];
        Object result = null;
        int index = 0;
        for (Class<?> paramType : paramTypes) {
            Class<?> paramTypeImpl = prototypeClasses.get(paramType);
            if (paramTypeImpl == null) {
                throw new BindingNotFoundException("Binding not found");
            }
            instances[index++] = getInstance(paramTypeImpl);
        }
        try {
            result =  constructor.newInstance(instances);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    private <T> Constructor<?> getConstructorWithInject(Class<T> impl) {
        Constructor<?> result = null;
        Constructor<?>[] constructors = impl.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                result = constructor;
            }
        }
        return result;
    }

    private <T> Constructor<?> getDefaultConstructor(Class<T> impl) {
        try {
            return impl.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new ConstructorNotFoundException("No default constructor");
        }
    }

    private <T> boolean hasConstructorWithInject(Class<T> impl) {
        Constructor<?>[] constructors = impl.getConstructors();
        for (Constructor<?> constructor : constructors) {
            boolean isPresent = constructor.isAnnotationPresent(Inject.class);
            if (isPresent) return true;
        }
        return false;
    }

    private <T> boolean hasMoreThanOneInjectConstructors(Class<? extends T> impl) {
        int count = 0;
        Constructor<?>[] constructors = impl.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                count++;
            }
        }
        return count > 1;
    }
}
