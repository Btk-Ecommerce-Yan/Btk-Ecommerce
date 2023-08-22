package com.btk.service;

import com.btk.entity.Order;
import com.btk.repository.IOrderRepository;
import com.btk.utility.ServiceManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends ServiceManager<Order,String> {

    private final IOrderRepository orderRepository;


    public OrderService( IOrderRepository orderRepository) {
        super(orderRepository);
        this.orderRepository = orderRepository;
    }
}
