package com.example.demo.models.view;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AllOrdersOrderViewModel {
    private LocalDateTime orderedOn;

    private String eventName;

    private String customerFirstName;

    private String customerLastName;

    private Integer ticketsCount;

    private BigDecimal eventPricePerTicket;

    public AllOrdersOrderViewModel() {
    }

    public LocalDateTime getOrderedOn() {
        return this.orderedOn;
    }

    public void setOrderedOn(LocalDateTime orderedOn) {
        this.orderedOn = orderedOn;
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCustomerFirstName() {
        return this.customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return this.customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public Integer getTicketsCount() {
        return this.ticketsCount;
    }

    public void setTicketsCount(Integer ticketsCount) {
        this.ticketsCount = ticketsCount;
    }

    public BigDecimal getEventPricePerTicket() {
        return this.eventPricePerTicket;
    }

    public void setEventPricePerTicket(BigDecimal eventPricePerTicket) {
        this.eventPricePerTicket = eventPricePerTicket;
    }
}
