package com.chen.tmall.comparator;

import com.chen.tmall.pojo.Product;

import java.util.Comparator;

public class ProductReviewComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getReviewCount()-p1.getReviewCount();
    }
}
