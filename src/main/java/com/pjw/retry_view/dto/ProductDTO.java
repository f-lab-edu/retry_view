package com.pjw.retry_view.dto;

import com.pjw.retry_view.entity.Category;
import com.pjw.retry_view.entity.Product;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private CategoryDTO mainCategory;
    private CategoryDTO subCategory;
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
    public ProductDTO(Long id, CategoryDTO mainCategory, CategoryDTO subCategory, String name, Integer price, String brand, String detail, String imageUrl, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
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

    public Product toEntity(){
        return Product.builder()
                .id(id)
                .mainCategory(categoryToEntity(mainCategory))
                .subCategory(categoryToEntity(subCategory))
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

    private Category categoryToEntity(CategoryDTO category){
        if(category == null) return null;
        return category.toEntity();
    }
}
