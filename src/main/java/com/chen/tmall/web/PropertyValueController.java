package com.chen.tmall.web;

import com.chen.tmall.pojo.Product;
import com.chen.tmall.pojo.PropertyValue;
import com.chen.tmall.service.ProductService;
import com.chen.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PropertyValueController {
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ProductService productService;

    @GetMapping("/products/{pid}/propertyValues")
    public List<PropertyValue> list(@PathVariable int pid)throws Exception{
        Product product = productService.get(pid);
        propertyValueService.init(product);
        List<PropertyValue>propertyValues= propertyValueService.list(product);
        return propertyValues;

    }
    @PutMapping("/propertyValues")
    public PropertyValue updata(@RequestBody PropertyValue propertyValue)throws Exception{
        propertyValueService.updata(propertyValue);
        return propertyValue;
    }
}
