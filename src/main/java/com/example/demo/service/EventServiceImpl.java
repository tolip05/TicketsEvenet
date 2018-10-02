package com.example.demo.service;

import com.example.demo.entities.Event;
import com.example.demo.entities.User;
import com.example.demo.models.service.EventServiceModel;
import com.example.demo.models.service.MyEventsServiceModel;
import com.example.demo.models.service.OrderServiceModel;
import com.example.demo.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    private final UserService userService;

    private final OrderService orderService;

    private final ModelMapper modelMapper;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, UserService userService,
                            OrderService orderService, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    private void placeOrder(Event event, User customer, Integer tickets) {
        OrderServiceModel orderServiceModel = new OrderServiceModel();

        orderServiceModel.setEvent(event);
        orderServiceModel.setOrderedOn(LocalDateTime.now());
        orderServiceModel.setCustomer(customer);
        orderServiceModel.setTicketsCount(tickets);

        this.orderService.createOrder(orderServiceModel);
    }

    @Override
    public boolean createEvents(EventServiceModel eventServiceModel) {
        Event eventEntity = this.modelMapper
                .map(eventServiceModel, Event.class);

        try {
            this.eventRepository.save(eventEntity);
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }

    @Override
    public void orderEvent(String eventId, String username, Integer tickets) {

        Event event = this.eventRepository.findById(eventId)
                .orElse(null);

        User customer =
                (User) this.userService.loadUserByUsername(username);
        if (event == null || customer == null) {
            throw new IllegalArgumentException("Order Event or Customer cannot be null!");
        }
        if (event.extractRemainingTickets() > tickets) {
            throw new IllegalArgumentException("Not enough tickets.");
        }
        event.setSoldTickets(event.getSoldTickets() + tickets);

        this.placeOrder(event, customer, tickets);
    }

    @Override
    public Set<EventServiceModel> getAll() {
        return this.eventRepository.findAll()
                .stream()
                .map(x -> this.modelMapper
                        .map(x, EventServiceModel.class))
                .collect(Collectors.toSet());

    }

    @Override
    public Set<EventServiceModel> getAvailable() {
        return this.eventRepository
                .findAll().stream()
                .filter(x -> x.getStartTime().compareTo(LocalDateTime.now())
                        > 0 && x.getTotalTickets() > 0)
                .map(x -> this.modelMapper
                        .map(x, EventServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<EventServiceModel> getUnavailable() {
        return this.eventRepository
                .findAll().stream()
                .filter(x -> x.getStartTime().compareTo(LocalDateTime.now())
                        <= 0 || x.getTotalTickets() < 0)
                .map(x -> this.modelMapper
                        .map(x, EventServiceModel.class)).collect(Collectors.toSet());
    }

    @Override
    public EventServiceModel getById(String id) {
        Event eventEntity = this.eventRepository
                .findById(id)
                .orElse(null);
        if (eventEntity == null) return null;
        EventServiceModel eventServiceModel =
                this.modelMapper.map(eventEntity, EventServiceModel.class);
        return eventServiceModel;
    }

    @Override
    public Set<MyEventsServiceModel> myEvents(String curentUser) {
        String userId = ((User) this.userService
                .loadUserByUsername(curentUser)).getId();
        Set<OrderServiceModel> allOrdersFromUser =
                this.orderService.getAllByUserId(userId);
        Set<MyEventsServiceModel> myEventsServiceModel = new HashSet<>();
        for (OrderServiceModel orderServiceModel : allOrdersFromUser) {
            MyEventsServiceModel resultModel1 =
                    this.modelMapper
                            .map(orderServiceModel.getEvent(), MyEventsServiceModel.class);
            resultModel1.setTickets(orderServiceModel.getTicketsCount());
            myEventsServiceModel.add(resultModel1);


        }
        return myEventsServiceModel;
    }
}
