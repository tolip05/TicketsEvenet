package com.example.demo.entities;



import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private LocalDateTime orderOn;

    private Event event;

    private User customer;

    private Integer ticketsCount;

    public Order() {
    }

    @Column(name = "ordered_on",nullable = false)
    public LocalDateTime getOrderOn() {
        return this.orderOn;
    }

    public void setOrderOn(LocalDateTime orderOn) {
        this.orderOn = orderOn;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id",nullable = false)
    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",nullable = false)
    public User getCustomer() {
        return this.customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    @Column(name = "tickets_count",nullable = false)
    public Integer getTicketsCount() {
        return this.ticketsCount;
    }

    public void setTicketsCount(Integer ticketsCount) {
        this.ticketsCount = ticketsCount;
    }
}
