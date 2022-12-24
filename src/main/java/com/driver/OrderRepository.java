package com.driver;

import java.util.*;

import org.apache.catalina.servlets.DefaultServlet.SortManager.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap = new HashMap<>();
    private HashMap<String,DeliveryPartner> deliverypartnerMap = new HashMap<>();
    private HashMap<String, List<Order>> partnerOrderMap = new HashMap<>();
    private HashMap<String, DeliveryPartner> orderPartnerMap = new HashMap<>();

    public void addOrder(Order order)
    {
        orderMap.put(order.getId(), order) ;
    }

    public void addDeliveryPartner(String partnerId)
    {
        DeliveryPartner partner = new DeliveryPartner(partnerId);
        deliverypartnerMap.put(partnerId, partner) ;
    }

    public void addOrderPartnerPair(String orderId, String partnerId)
    {
        List<Order> pairlist = new ArrayList<>();

        if(partnerOrderMap.containsKey(partnerId))
        pairlist = partnerOrderMap.get(partnerId) ;

        if(!pairlist.contains(orderMap.get(orderId)))
        pairlist.add(orderMap.get(orderId));

        partnerOrderMap.put(partnerId, pairlist) ;

        DeliveryPartner partner = deliverypartnerMap.get(partnerId) ;
        orderPartnerMap.put(orderId, partner) ;
        partner.setNumberOfOrders(pairlist.size());
    }

    public Order getOrderById(String id)
    {
       return orderMap.get(id) ;
    }

    public DeliveryPartner getPartnerById(String id)
    {
       return deliverypartnerMap.get(id) ;
    }

    public int getOrderCountByPartnerId(String partnerId)
    {
        int count = partnerOrderMap.get(partnerId).size() ;
        return count ;
    }
    public List<String> getOrdersByPartnerId(String partnerId)
    {
        List<Order> partnerorders = new ArrayList<>();
        partnerorders = partnerOrderMap.get(partnerId) ;
        List<String> orders = new ArrayList<>() ;
        for(int i= 0 ; i< partnerorders.size() ;i++)
        {
            orders.add(partnerorders.get(i).getId()) ;
        }
        return orders ;
    }

    public List<String> getAllOrders()
    {
        List<String> orderlist = new ArrayList<>();
        for(String id:orderMap.keySet())
        {
            orderlist.add(id);
        }
        return orderlist ;
    }

    public int getCountOfUnassignedOrders()
    {
        int count= 0 ;
        for(String id: orderMap.keySet())
        {
            if(!orderPartnerMap.containsKey(id))
            count++ ;
        }
        return count ;
    }
    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId)
    {
        int currenttime = Integer.parseInt(time.substring(0, 2))*60 + Integer.parseInt(time.substring(2,4)) ;
        List<Order> orderlist = new ArrayList<>();
        orderlist = partnerOrderMap.get(partnerId);
        int count =0 ;
        for(int i = 0 ; i<orderlist.size() ; i++)
        {
            if(orderlist.get(i).getDeliveryTime() > currenttime)
            count++ ;
        }
        return count ;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId)
    {
        List<Order> order = new ArrayList<>();
        order = partnerOrderMap.get(partnerId) ;
        List<Integer> time = new ArrayList<>();
        for(int i= 0 ; i < order.size() ; i++)
        {
            int t = order.get(i).getDeliveryTime();
            time.add(t);
        }
        int maxtime = 0 ;
        for(int i = 0 ; i<time.size() ; i++)
        {
            maxtime = Math.max(time.get(i), maxtime) ;
        }
        int hour = maxtime/60 ;
        int min = maxtime%60 ;

        String hour1 = Integer.toString(hour) ;
        String min1 = Integer.toString(min) ;
        if(hour1.length()==1){
            hour1 = "0"+hour1 ;
        }
        if(min1.length()==1){
            min1 = "0"+min1;
        }

        String finalTime = hour1+":"+ min1 ;
        return finalTime ;
    }
    public void deletePartnerById(String partnerId)
    {
        List<Order> orders = new ArrayList<>();
        orders = partnerOrderMap.get(partnerId) ;
        for(int i = 0 ; i < orders.size() ; i++)
        {
            orderPartnerMap.remove((orders.get(i)).getId()) ;
        }
        partnerOrderMap.remove(partnerId) ;
        deliverypartnerMap.remove(partnerId) ;
    }
    public void deleteOrderById(String orderId)
    {
        Order order = orderMap.get(orderId) ;
        if(orderPartnerMap.containsKey(orderId))
        {
            DeliveryPartner partner = orderPartnerMap.get(orderId) ;
            List<Order> partnerorderlist = new ArrayList<>() ;
            partnerorderlist = partnerOrderMap.get(partner.getId());
            partnerorderlist.remove(order);
            partnerOrderMap.put(partner.getId(), partnerorderlist) ;
            partner.setNumberOfOrders(partnerorderlist.size());
            orderPartnerMap.remove(orderId);
        }
        orderMap.remove(orderId);
    }

}
