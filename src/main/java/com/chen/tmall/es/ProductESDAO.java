package com.chen.tmall.es;

import com.chen.tmall.pojo.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductESDAO extends ElasticsearchRepository<Product,Integer> {
}
