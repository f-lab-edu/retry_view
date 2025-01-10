package com.pjw.retry_view.spec;

import com.pjw.retry_view.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> eqMainCate(Long mainCateId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("mainCateId"), mainCateId);
    }

    public static Specification<Product> eqSubCate(Long subCateId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("subCateId"), subCateId);
    }

    public static Specification<Product> likeBrand(String brand){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("brand"), brand);
    }

    public static Specification<Product> likeName(String name){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), name);
    }

    private ProductSpecification(){}
}
