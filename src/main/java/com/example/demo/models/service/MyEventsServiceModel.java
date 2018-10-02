package com.example.demo.models.service;

import java.time.LocalDateTime;

public class MyEventsServiceModel {
    private String id;

    private String name;

    private String place;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer tickets;

    public MyEventsServiceModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getTickets() {
        return this.tickets;
    }

    public void setTickets(Integer tickets) {
        this.tickets = tickets;
    }
}
