package com.chen.tmall.service;

import com.chen.tmall.dao.PropertyDAO;
import com.chen.tmall.dao.PropertyValueDAO;
import com.chen.tmall.pojo.Category;
import com.chen.tmall.pojo.Product;
import com.chen.tmall.pojo.Property;
import com.chen.tmall.pojo.PropertyValue;
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
@CacheConfig(cacheNames="propertyValues")
public class PropertyValueService {
    @Autowired
    PropertyValueDAO propertyValueDAO;
    @Autowired
    PropertyService propertyService;

    @Cacheable(key="'propertyValues-one-'+ #p0")
    public PropertyValue get(int id){
        return propertyValueDAO.findById(id).get();
    }
    @CacheEvict(allEntries=true)
    public void updata(PropertyValue bean){
        propertyValueDAO.save(bean);
    }
    @Cacheable(key="'propertyValues-pid-'+ #p0.id")
    public List<PropertyValue> list(Product product){
        return propertyValueDAO.findByProductOrderByIdDesc(product);
    }
    @Cacheable(key="'propertyValues-one-pid-'+#p0.id+ '-ptid-' + #p1.id")
    public PropertyValue getByPropertyAndProduct(Property property, Product product){
        return propertyValueDAO.getByPropertyAndProduct(property,product);
    }

    public void init(Product product){
        Category category = product.getCategory();
        List<Property> properties=propertyService.listByCategory(category);
        for (Property property:properties){
            PropertyValue propertyValue = getByPropertyAndProduct(property,product);
            if(null==propertyValue){
                propertyValue = new PropertyValue();
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);
                propertyValueDAO.save(propertyValue);
            }
        }
    }
}
