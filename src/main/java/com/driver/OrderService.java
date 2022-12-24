package com.driver;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderrepository ;

    public void addOrder(Order order)
    {
        orderrepository.addOrder(order) ;
    }

    public void addDeliveryPartner(String partnerId)
    {
        orderrepository.addDeliveryPartner(partnerId) ;
    }

    public void addOrderPartnerPair(String orderId, String partnerId)
    {
        orderrepository.addOrderPartnerPair(orderId, partnerId);
    }

    public Order getOrderById(String id)
    {
        return orderrepository.getOrderById(id) ;
    }

    public DeliveryPartner getPartnerById(String id)
    {
       return orderrepository.getPartnerById(id) ;
    }

    public int getOrderCountByPartnerId(String partnerId)
    {
        int count = 0;
        count = orderrepository.getOrderCountByPartnerId(partnerId) ;
        return count ;
    }
    public List<String> getOrdersByPartnerId(String partnerId)
    {
        List<String> orders = new ArrayList<>() ;
        orders = orderrepository.getOrdersByPartnerId(partnerId) ;
        return orders ;
    }

    public List<String> getAllOrders()
    {
        List<String> orderlist = new ArrayList<>();
        orderlist = orderrepository.getAllOrders() ;
        return orderlist ;
    }
    public int getCountOfUnassignedOrders()
    {
        int count = 0 ;
        count = orderrepository.getCountOfUnassignedOrders() ;
        return count ;
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId)
    {
        return orderrepository.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId) ;
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId)
    {
        return orderrepository.getLastDeliveryTimeByPartnerId(partnerId) ;
    }
    public void deletePartnerById(String partnerId)
    {
        orderrepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId)
    {
        orderrepository.deleteOrderById(orderId);
    }
    
}
