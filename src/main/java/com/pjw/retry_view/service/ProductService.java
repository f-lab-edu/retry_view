package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.ProductView;
import com.pjw.retry_view.entity.Category;
import com.pjw.retry_view.entity.Product;
import com.pjw.retry_view.exception.ResourceNotFoundException;
import com.pjw.retry_view.repositoryImpl.CategoryRepositoryImpl;
import com.pjw.retry_view.repositoryImpl.ProductRepositoryImpl;
import com.pjw.retry_view.request.ProductRequest;
import com.pjw.retry_view.spec.ProductSpecification;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepositoryImpl productRepositoryImpl;
    private final CategoryRepositoryImpl categoryRepositoryImpl;
    private static final int DEFAULT_PAGE_SIZE = 6;

    public ProductService(ProductRepositoryImpl productRepositoryImpl, CategoryRepositoryImpl categoryRepositoryImpl) {
        this.productRepositoryImpl = productRepositoryImpl;
        this.categoryRepositoryImpl = categoryRepositoryImpl;
    }

    public List<ProductView> getProductList(Long mainCateId, Long subCateId, String brand, String name){
        Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE, Sort.Direction.ASC, "id");
        Specification<Product> spec = (root, query, criteriaBuilder) -> null;

        if(mainCateId != null){
            spec = spec.and(ProductSpecification.eqMainCate(mainCateId));
        }
        if(subCateId != null){
            spec = spec.and(ProductSpecification.eqSubCate(subCateId));
        }
        if(brand != null && StringUtils.isBlank(brand)){
            spec = spec.and(ProductSpecification.likeBrand(brand));
        }
        if(name != null && StringUtils.isBlank(name)){
            spec = spec.and(ProductSpecification.likeName(name));
        }

        Slice<Product> productList = productRepositoryImpl.findAll(spec, pageable);

        return productList.getContent().stream().map(Product::toDTO).toList();
    }

    public ProductView getProduct(Long id){
        return productRepositoryImpl.findById(id).orElseThrow(ResourceNotFoundException::new).toDTO();
    }

    @Transactional
    public ProductView saveProduct(ProductRequest req){
        Category mainCategory = categoryRepositoryImpl.findById(req.getMainCategoryId()).orElse(null);
        Category subCategory = null;
        if(req.getSubCategoryId() != null) {
            subCategory = categoryRepositoryImpl.findById(req.getSubCategoryId()).orElse(null);
        }
        Product product = Product.newOne(mainCategory, subCategory, req.getName(), req.getPrice(), req.getDetail(), req.getBrand(), req.getImageUrl(), req.getCreatedBy());
        return productRepositoryImpl.save(product).toDTO();
    }

    @Transactional
    public ProductView updateProduct(ProductRequest req){
        Category mainCategory = categoryRepositoryImpl.findById(req.getMainCategoryId()).orElse(null);
        Category subCategory = null;
        if(req.getSubCategoryId() != null) {
            categoryRepositoryImpl.findById(req.getSubCategoryId()).orElse(null);
        }
        Product product = Product.updateProduct(req.getId(), mainCategory, subCategory, req.getName(), req.getPrice(), req.getDetail(), req.getBrand(), req.getImageUrl(), req.getUpdatedBy());
        return productRepositoryImpl.save(product).toDTO();
    }

    @Transactional
    public void deleteProduct(Long id){
        productRepositoryImpl.deleteById(id);
    }
}
