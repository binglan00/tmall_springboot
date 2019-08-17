package com.chen.tmall.web;

import com.chen.tmall.pojo.Property;
import com.chen.tmall.service.PropertyService;
import com.chen.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PropertyController {
    @Autowired
    PropertyService propertyService;
    @GetMapping("/categories/{cid}/properties")
    public Page4Navigator<Property> list(@PathVariable int cid, @RequestParam(value ="start",defaultValue ="0")int start,
                                         @RequestParam(value ="size",defaultValue ="5")int size)throws Exception{
        start= start<0?0:start;
        Page4Navigator<Property> page = propertyService.list(cid,start,size,5);
        return page;
    }
    @PostMapping ("/properties")
    public Property add(@RequestBody Property bean)throws Exception{
        propertyService.add(bean);
        return bean;
    }

    @DeleteMapping("/properties/{id}")
    public String delete(@PathVariable int id){
        propertyService.delete(id);
        return null;
    }

    @PutMapping("/properties")
    public Property updata(@RequestBody Property bean){
        propertyService.updata(bean);
        return bean;
    }

    @GetMapping("/properties/{id}")
    public Property get(@PathVariable int id){
        Property bean= propertyService.get(id);
        return bean;
    }
}
