package com.chen.tmall.web;

import com.chen.tmall.pojo.Order;
import com.chen.tmall.service.OrderItemService;
import com.chen.tmall.service.OrderService;
import com.chen.tmall.util.Page4Navigator;
import com.chen.tmall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;


@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @GetMapping("/orders")
    public Page4Navigator<Order> list(@RequestParam(name="start",defaultValue = "0") int start,
                      @RequestParam(name="size",defaultValue = "5")int size)throws Exception{
        start=start<0?0:start;
        Page4Navigator<Order> page= orderService.list(start,size,5);
        orderItemService.fill(page.getContent());
        orderService.removeOrderFromOrderItem(page.getContent());
        return page;
    }
    @PutMapping("deliveryOrder/{oid}")
    public Object deliveryOrder(@PathVariable int oid) throws IOException {
        Order o = orderService.get(oid);
        o.setDeliveryDate(new Date());
        o.setStatus(OrderService.waitConfirm);
        orderService.update(o);
        return Result.success();
    }
}
