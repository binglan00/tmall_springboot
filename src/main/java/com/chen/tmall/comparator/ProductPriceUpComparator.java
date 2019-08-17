package com.chen.tmall.comparator;

import com.chen.tmall.pojo.Product;

import java.util.Comparator;

public class ProductPriceUpComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return (int) (p2.getPromotePrice()-p1.getPromotePrice());
    }
}
