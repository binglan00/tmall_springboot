package com.chen.tmall.web;

import com.chen.tmall.pojo.Category;
import com.chen.tmall.service.CategoryService;
import com.chen.tmall.util.ImageUtil;
import com.chen.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public Page4Navigator<Category> list(@RequestParam (value ="start",defaultValue ="0") int start,
                                         @RequestParam (value ="size",defaultValue = "8" )int size)throws Exception{
        //start开始的页数，size每一页显示数据的数量
        start=start<0?0:start;
        Page4Navigator<Category>  page =  categoryService.list(start,size,5);//这里这个5是下面显示出来的可以直接电机的页数是多少个，【1,2,3,4,5】
        return page;
    }

    @PostMapping("/categories")
    public Object add(Category bean, MultipartFile image, HttpServletRequest request) throws Exception{//可以获取到的是data4vue里面的数据
        categoryService.add(bean);
        saveOrUpdateImageFile(bean, image, request);
        return bean;
    }

    public void saveOrUpdateImageFile(Category bean, MultipartFile image, HttpServletRequest request)
            throws IOException {
        File imageFolder= new File(request.getServletContext().getRealPath("img/category"));//获取工程项目所在路径
        File file = new File(imageFolder,bean.getId()+".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img,"jpg",file);
    }
    @DeleteMapping ("/categories/{id}")
    public String  delete(@PathVariable("id") int id,HttpServletRequest request) throws Exception{
        categoryService.delete(id);
        File imageFolder= new File(request.getServletContext().getRealPath("img/category"));//获取工程项目所在路径
        File file = new File(imageFolder,id+".jpg");
        if(file.exists())
            file.delete();
        return null;
    }

    @GetMapping("/categories/{id}")
    public Category get(@PathVariable("id") int id){
        return categoryService.get(id);
    }

    @PutMapping("/categories/{id}")
    public Object update(Category bean, MultipartFile image, HttpServletRequest request) throws Exception {
        categoryService.update(bean);
        if(image!=null){
            saveOrUpdateImageFile(bean, image, request);
        }
        return bean;
    }

}
