package by.hpk.task.container.providing;

public class ProviderImpl<T> implements Provider<T> {
    private final T instance;

    public ProviderImpl(T instance) {
        this.instance = instance;
    }

    @Override
    public T getInstance() {
        return instance;
    }

}
