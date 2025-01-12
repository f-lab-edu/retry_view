package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.ProductView;
import com.pjw.retry_view.entity.Category;
import com.pjw.retry_view.entity.Product;
import com.pjw.retry_view.exception.ResourceNotFoundException;
import com.pjw.retry_view.repositoryImpl.CategoryRepositoryImpl;
import com.pjw.retry_view.repositoryImpl.ProductRepositoryImpl;
import com.pjw.retry_view.request.ProductRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepositoryImpl productRepositoryImpl;
    private final CategoryRepositoryImpl categoryRepositoryImpl;

    public ProductService(ProductRepositoryImpl productRepositoryImpl, CategoryRepositoryImpl categoryRepositoryImpl) {
        this.productRepositoryImpl = productRepositoryImpl;
        this.categoryRepositoryImpl = categoryRepositoryImpl;
    }

    public List<ProductView> getProductList(){
        return productRepositoryImpl.findAll().stream().map(Product::toDTO).toList();
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
