package com.example.demo.models.service;

import com.example.demo.entities.Event;
import com.example.demo.entities.User;

import java.time.LocalDateTime;

public class OrderServiceModel {
    private String id;

    private LocalDateTime orderedOn;

    private Event event;

    private User customer;

    private Integer ticketsCount;

    public OrderServiceModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getOrderedOn() {
        return this.orderedOn;
    }

    public void setOrderedOn(LocalDateTime orderedOn) {
        this.orderedOn = orderedOn;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getCustomer() {
        return this.customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Integer getTicketsCount() {
        return this.ticketsCount;
    }

    public void setTicketsCount(Integer ticketsCount) {
        this.ticketsCount = ticketsCount;
    }
}
