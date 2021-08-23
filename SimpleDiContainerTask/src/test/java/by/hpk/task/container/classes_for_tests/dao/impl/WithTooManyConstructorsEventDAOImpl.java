package by.hpk.task.container.classes_for_tests.dao.impl;

import by.hpk.task.container.annotation.Inject;
import by.hpk.task.container.classes_for_tests.dao.EventDAO;

public class WithTooManyConstructorsEventDAOImpl implements EventDAO {
    @Inject
    public WithTooManyConstructorsEventDAOImpl(Object o){}

    @Inject
    public WithTooManyConstructorsEventDAOImpl(String s ){}
}
