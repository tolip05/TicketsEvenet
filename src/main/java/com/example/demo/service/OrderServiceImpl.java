package com.example.demo.service;

import com.example.demo.entities.Order;
import com.example.demo.models.service.OrderServiceModel;
import com.example.demo.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean createOrder(OrderServiceModel orderServiceModel) {
        Order orderEntity = this.modelMapper
                .map(orderServiceModel,Order.class);

        try{
            this.orderRepository.save(orderEntity);
        }catch (Exception ignored){
            return false;
        }
        return true;
    }

    @Override
    public Set<OrderServiceModel> getAll() {
       return this.orderRepository.findAll().stream()
                .map(x-> this.modelMapper
                .map(x,OrderServiceModel.class))
        .collect(Collectors.toSet());


    }

    @Override
    public Set<OrderServiceModel> getAllByUserId(String userId) {
        return this.orderRepository.findAllByUserId(userId)
                .stream().map(x-> this.modelMapper
                .map(x,OrderServiceModel.class))
                .collect(Collectors.toSet());

    }
}
