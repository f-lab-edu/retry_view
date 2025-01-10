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
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Integer price;
    @Column(name = "brand")
    private String brand;
    @Column(name = "detail")
    private String detail;
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "updated_by")
    private Long updatedBy;
    @Column(name = "updated_at")
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
