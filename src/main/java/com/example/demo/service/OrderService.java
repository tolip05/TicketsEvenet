package com.example.demo.service;

import com.example.demo.models.service.OrderServiceModel;

import java.util.Set;

public interface OrderService {
boolean createOrder(OrderServiceModel orderServiceModel);
Set<OrderServiceModel>getAll();
Set<OrderServiceModel>getAllByUserId(String userId);
}
