package com.chen.tmall.service;

import com.chen.tmall.dao.CategoryDAO;
import com.chen.tmall.pojo.Category;
import com.chen.tmall.pojo.Product;
import com.chen.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames="categories")
public class CategoryService {
    @Autowired
    CategoryDAO categoryDAO;
    @Cacheable(key="'categories-all'")
    public List<Category> list(){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        return categoryDAO.findAll(sort);
    }
    @Cacheable(key="'categories-page-'+#p0+ '-' + #p1")
    public Page4Navigator<Category> list(int start, int size,int navigatePages){
        //start开始的页数，size每一页显示数据的数量
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = new PageRequest(start,size,sort);
        Page pageFromJPA = categoryDAO.findAll(pageable);
        Page4Navigator<Category> page4Navigator = new Page4Navigator<>(pageFromJPA,navigatePages);
        return page4Navigator;
    }
    @CacheEvict(allEntries=true)
    public void add(Category category){
        categoryDAO.save(category);
    }
    @CacheEvict(allEntries=true)
    public  void delete(int id){
        categoryDAO.deleteById(id);
    }
    @Cacheable(key="'categories-one-'+ #p0")
    public Category get(int id){
       return categoryDAO.findById(id).get();
    }
    @CacheEvict(allEntries=true)
    public void update(Category category){
        categoryDAO.save(category);
    }

    public  void removeCategoryFromProduct(Category category){
        List<Product> products = category.getProducts();
        for(Product product:products){
            product.setCategory(null);
        }
    }

    public void removeCategoryFromProduct(List<Category> cs){
        for (Category category:cs){
            removeCategoryFromProduct(category);
        }
    }

}
