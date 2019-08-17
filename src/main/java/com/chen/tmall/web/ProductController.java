package com.chen.tmall.web;

import com.chen.tmall.pojo.Product;
import com.chen.tmall.service.ProductImageService;
import com.chen.tmall.service.ProductService;
import com.chen.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductImageService productImageService;
    @PostMapping("/products")
    public Product add(@RequestBody Product bean)throws Exception{
        productService.add(bean);
        return bean;
    }
    @PutMapping("/products")
    public Product updata(@RequestBody Product bean)throws Exception{
        productService.updata(bean);
        return bean;
    }
    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable int id)throws Exception{
        productService.delete(id);
        return null;
    }
    @GetMapping("/products/{id}")
    public Product get(@PathVariable int id)throws Exception{
        return  productService.get(id);
    }
    @GetMapping("categories/{cid}/products")
    public Page4Navigator<Product> list(@PathVariable int cid,@RequestParam(value = "start" ,defaultValue="0") int start,
                                        @RequestParam(value = "size" ,defaultValue="5")int size)throws Exception{
        start=start<0?0:start;
        Page4Navigator<Product>  page=productService.list(cid,start,size,5);

        productImageService.setFirstProdutImages(page.getContent());
        return page;
    }
}
