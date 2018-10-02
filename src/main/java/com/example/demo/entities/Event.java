package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name = "events")
public class Event extends BaseEntity{

    private String name;

    private String place;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int totalTickets;

    private int soldTickets;

    private BigDecimal pricePerTicket;

    public Event() {
    }

    @Column(name = "name",nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "place",nullable = false)
    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
@Column(name = "start_time",nullable = false)
    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time",nullable = false)
    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    @Column(name = "total_tickets",nullable = false)
    public int getTotalTickets() {
        return this.totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    @Column(name = "sold_tickets",nullable = false)
    public int getSoldTickets() {
        return this.soldTickets;
    }

    public void setSoldTickets(int soldTickets) {
        this.soldTickets = soldTickets;
    }
    @Column(name = "price_per_ticket", nullable = false)
    public BigDecimal getPricePerTicket() {
        return this.pricePerTicket;
    }

    public void setPricePerTicket(BigDecimal pricePerTicket) {
        this.pricePerTicket = pricePerTicket;
    }

    public Integer extractRemainingTickets(){
        return this.totalTickets - this.soldTickets;
    }
}
