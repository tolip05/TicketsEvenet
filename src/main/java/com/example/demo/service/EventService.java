package com.example.demo.service;

import com.example.demo.models.service.EventServiceModel;
import com.example.demo.models.service.MyEventsServiceModel;

import java.util.Set;

public interface EventService {
    boolean createEvents(EventServiceModel eventServiceModel);
    void orderEvent(String eventId,String username,Integer tickets);
    Set<EventServiceModel>getAll();
    Set<EventServiceModel>getAvailable();
    Set<EventServiceModel>getUnavailable();
    EventServiceModel getById(String id);
    Set<MyEventsServiceModel>myEvents(String curentUser);


}
