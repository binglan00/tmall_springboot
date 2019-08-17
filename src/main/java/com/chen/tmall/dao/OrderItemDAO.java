package com.chen.tmall.dao;

import com.chen.tmall.pojo.Order;
import com.chen.tmall.pojo.OrderItem;
import com.chen.tmall.pojo.Product;
import com.chen.tmall.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemDAO extends JpaRepository<OrderItem,Integer> {
    List<OrderItem> findByOrderOrderByIdDesc(Order order);
    List<OrderItem> findByProduct(Product product);
    List<OrderItem> findByUserAndOrderIsNull(User user);
}
