package by.hpk.task.container.injection;

import by.hpk.task.container.providing.Provider;

public interface Injector {

    <T> Provider<T> getProvider(Class<T> type);

    <T> void bind(Class<T> intf, Class<? extends T> impl);

    <T> void bindSingleton(Class<T> intf, Class<? extends T> impl);
}
