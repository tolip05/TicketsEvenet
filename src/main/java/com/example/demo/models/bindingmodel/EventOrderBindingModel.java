package com.example.demo.models.bindingmodel;

public class EventOrderBindingModel {
    private String eventId;

    private Integer tickets;

    public EventOrderBindingModel() {
    }

    public String getEventId() {
        return this.eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Integer getTickets() {
        return this.tickets;
    }

    public void setTickets(Integer tickets) {
        this.tickets = tickets;
    }
}
