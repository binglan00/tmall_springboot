package com.chen.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name="product")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@Document(indexName = "tmall_springboot",type = "product")
public class Product {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "cid")
    private Category category;
    //如果既没有指明 关联到哪个Column,又没有明确要用@Transient忽略，那么就会自动关联到表对应的同名字段
    private String name;
    private String subTitle;
    private float originalPrice;
    private float promotePrice;
    private int stock;
    private Date createDate;

    @Transient
    private ProductImage firstProductImage;
    @Transient
    private List<ProductImage> productSingleImages;
    @Transient
    private List<ProductImage> productDetailImages;
    @Transient
    private int saleCount;

    public void setProductSingleImages(List<ProductImage> productSingleImages) {
        this.productSingleImages = productSingleImages;
    }

    public void setProductDetailImages(List<ProductImage> productDetailImages) {
        this.productDetailImages = productDetailImages;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public List<ProductImage> getProductSingleImages() {
        return productSingleImages;
    }

    public List<ProductImage> getProductDetailImages() {
        return productDetailImages;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    @Transient
    private int reviewCount;

    public ProductImage getFirstProductImage() {
        return firstProductImage;
    }

    public void setFirstProductImage(ProductImage firstProductImage) {
        this.firstProductImage = firstProductImage;
    }



    public int getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setOriginalPrice(float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setPromotePrice(float promotePrice) {
        this.promotePrice = promotePrice;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public float getOriginalPrice() {
        return originalPrice;
    }

    public float getPromotePrice() {
        return promotePrice;
    }

    public int getStock() {
        return stock;
    }

    public Date getCreateDate() {
        return createDate;
    }
}
