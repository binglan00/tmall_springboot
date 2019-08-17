package com.chen.tmall.service;

import com.chen.tmall.dao.OrderItemDAO;
import com.chen.tmall.pojo.Order;
import com.chen.tmall.pojo.OrderItem;
import com.chen.tmall.pojo.Product;
import com.chen.tmall.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames="orderItems")
public class OrderItemService {
    @Autowired
    OrderItemDAO orderItemDAO;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    ProductService productService;
    @Cacheable(key="'orderItems-oid-'+ #p0.id")
    public List<OrderItem> listByOrder(Order order){
       return orderItemDAO.findByOrderOrderByIdDesc(order);
    }

    public void fill(List<Order> orders) {
        for (Order order : orders)
            fill(order);
    }

    public void fill(Order order) {
        List<OrderItem> orderItems = listByOrder(order);
        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi :orderItems) {
            total+=oi.getNumber()*oi.getProduct().getPromotePrice();
            totalNumber+=oi.getNumber();
            productImageService.setFirstProdutImage(oi.getProduct());
        }
        order.setTotal(total);
        order.setOrderItems(orderItems);
        order.setTotalNumber(totalNumber);
    }
    @Cacheable(key="'orderItems-pid-'+ #p0.id")
    public List<OrderItem> listByProduct(Product product) {
        return orderItemDAO.findByProduct(product);
    }

    public int getSaleCount(Product product) {
        List<OrderItem> ois =listByProduct(product);
        int result =0;
        for (OrderItem oi : ois) {
            if(null!=oi.getOrder())
                if(null!= oi.getOrder() && null!=oi.getOrder().getPayDate())
                    result+=oi.getNumber();//每一单都有不一样的数量每一单的数量加起来才是总销量
        }
        return result;
    }
    @Cacheable(key="'orderItems-uid-'+ #p0.id")
    public List<OrderItem> listByUser(User user){
        return orderItemDAO.findByUserAndOrderIsNull(user);
    }
    @CacheEvict(allEntries=true)
    public void update(OrderItem o){
        orderItemDAO.save(o);
    }
    @CacheEvict(allEntries=true)
    public void add(OrderItem o){
        orderItemDAO.save(o);
    }
    @Cacheable(key="'orderItems-one-'+ #p0")
    public OrderItem get(int id){
        return orderItemDAO.findById(id).get();
    }
    @CacheEvict(allEntries=true)
    public  void delete(int id){
        OrderItem oi=get(id);
        orderItemDAO.delete(oi);
    }


}
