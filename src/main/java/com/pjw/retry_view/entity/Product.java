package com.pjw.retry_view.entity;

import com.pjw.retry_view.dto.CategoryView;
import com.pjw.retry_view.dto.ProductView;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "main_cate_id")
    private Category mainCategory;
    @OneToOne
    @JoinColumn(name = "sub_cate_id")
    private Category subCategory;
    private String name;
    private Integer price;
    private String brand;
    private String detail;
    private String imageUrl;

    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
    private ZonedDateTime updatedAt;

    @Builder
    public Product(Long id, Category mainCategory, Category subCategory, String name, Integer price, String brand, String detail, String imageUrl, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.detail = detail;
        this.imageUrl = imageUrl;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public static Product newOne(Category mainCategory, Category subCategory, String name, Integer price, String detail, String brand, String imageUrl, Long createdBy){
        return Product.builder()
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .name(name)
                .price(price)
                .detail(detail)
                .brand(brand)
                .imageUrl(imageUrl)
                .createdBy(createdBy)
                .createdAt(ZonedDateTime.now())
                .build();
    }

    public static Product updateProduct(Long id, Category mainCategory, Category subCategory, String name, Integer price, String detail, String brand, String imageUrl, Long updatedBy){
        return Product.builder()
                .id(id)
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .name(name)
                .price(price)
                .detail(detail)
                .brand(brand)
                .imageUrl(imageUrl)
                .updatedBy(updatedBy)
                .updatedAt(ZonedDateTime.now())
                .build();
    }

    public ProductView toDTO(){
        return ProductView.builder()
                .id(id)
                .mainCategory(categoryToDTO(mainCategory))
                .subCategory(categoryToDTO(subCategory))
                .name(name)
                .price(price)
                .brand(brand)
                .detail(detail)
                .imageUrl(imageUrl)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .build();
    }

    private CategoryView categoryToDTO(Category category){
        if(category == null) return null;
        return category.toDTO();
    }
}
