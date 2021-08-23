package by.hpk.task.container.classes_for_tests.service.impl;

import by.hpk.task.container.annotation.Inject;
import by.hpk.task.container.classes_for_tests.dao.EventDAO;
import by.hpk.task.container.classes_for_tests.service.EventService;

public class EventServiceImpl implements EventService {
    @Inject
    public EventServiceImpl(EventDAO dao){

    }
}
