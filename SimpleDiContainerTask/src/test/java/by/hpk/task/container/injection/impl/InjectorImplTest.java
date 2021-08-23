package by.hpk.task.container.injection.impl;

import by.hpk.task.container.classes_for_tests.dao.EventDAO;
import by.hpk.task.container.classes_for_tests.dao.impl.EventDAOImpl;
import by.hpk.task.container.classes_for_tests.dao.impl.WithTooManyConstructorsEventDAOImpl;
import by.hpk.task.container.classes_for_tests.dao.impl.WithDefaultConstructorEventDAOImpl;
import by.hpk.task.container.classes_for_tests.dao.impl.WithoutDefaultConstructorEventDAOImpl;
import by.hpk.task.container.classes_for_tests.service.EventService;
import by.hpk.task.container.classes_for_tests.service.impl.EventServiceImpl;
import by.hpk.task.container.exception.BindingNotFoundException;
import by.hpk.task.container.exception.ConstructorNotFoundException;
import by.hpk.task.container.exception.TooManyConstructorsException;
import by.hpk.task.container.injection.Injector;
import by.hpk.task.container.providing.Provider;
import org.junit.Test;

import static org.junit.Assert.*;

public class InjectorImplTest {

    @Test
    public void getProviderShouldNotReturnNullTest() {
        Injector injector = new InjectorImpl();
        injector.bind(EventDAO.class, EventDAOImpl.class);
        Provider<EventDAO> daoProvider = injector.getProvider(EventDAO.class);
        assertNotNull(daoProvider);
        assertNotNull(daoProvider.getInstance());
        assertSame(EventDAOImpl.class, daoProvider.getInstance().getClass());
    }

    @Test
    public void getProviderShouldReturnNullTest() {
        Injector injector = new InjectorImpl();
        Provider<EventDAO> daoProvider = injector.getProvider(EventDAO.class);
        assertNull(daoProvider);
    }

    @Test(expected = ConstructorNotFoundException.class)
    public void getProviderShouldThrowConstructorNotFoundExceptionTest() {
        Injector injector = new InjectorImpl();
        injector.bind(EventDAO.class, WithoutDefaultConstructorEventDAOImpl.class);
        injector.getProvider(EventDAO.class);
    }

    @Test
    public void getProviderShouldNotThrowConstructorNotFoundExceptionTest() {
        Injector injector = new InjectorImpl();
        injector.bind(EventDAO.class, WithDefaultConstructorEventDAOImpl.class);
        injector.getProvider(EventDAO.class);
    }

    @Test(expected = BindingNotFoundException.class)
    public void getProviderShouldThrowBindingNotFoundExceptionTest() {
        Injector injector = new InjectorImpl();
        injector.bind(EventService.class, EventServiceImpl.class);
        injector.getProvider(EventService.class);
    }

    @Test
    public void getProviderShouldNotThrowBindingNotFoundExceptionTest() {
        Injector injector = new InjectorImpl();
        injector.bind(EventService.class, EventServiceImpl.class);
        injector.bind(EventDAO.class, EventDAOImpl.class);
        injector.getProvider(EventService.class);
        injector.getProvider(EventDAO.class);
    }

    @Test(expected = TooManyConstructorsException.class)
    public void bindShouldThrowTooManyConstructorsExceptionTest() {
        Injector injector = new InjectorImpl();
        injector.bind(EventDAO.class, WithTooManyConstructorsEventDAOImpl.class);
    }

    @Test
    public void bindShouldNotThrowTooManyConstructorsExceptionTest() {
        Injector injector = new InjectorImpl();
        injector.bind(EventDAO.class, EventDAOImpl.class);
    }

    @Test(expected = TooManyConstructorsException.class)
    public void bindSingletonShouldThrowTooManyConstructorsExceptionTest() {
        Injector injector = new InjectorImpl();
        injector.bindSingleton(EventDAO.class, WithTooManyConstructorsEventDAOImpl.class);
    }

    @Test
    public void bindSingletonShouldNotThrowTooManyConstructorsExceptionTest() {
        Injector injector = new InjectorImpl();
        injector.bindSingleton(EventDAO.class, EventDAOImpl.class);
    }
}