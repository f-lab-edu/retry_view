package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.ProductView;
import com.pjw.retry_view.entity.Category;
import com.pjw.retry_view.entity.Product;
import com.pjw.retry_view.exception.ResourceNotFoundException;
import com.pjw.retry_view.repository.CategoryRepository;
import com.pjw.retry_view.repository.ProductRepository;
import com.pjw.retry_view.request.ProductRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductView> getProductList(){
        return productRepository.findAll().stream().map(Product::toDTO).toList();
    }

    public ProductView getProduct(Long id){
        return productRepository.findById(id).orElseThrow(ResourceNotFoundException::new).toDTO();
    }

    @Transactional
    public ProductView saveProduct(ProductRequest req){
        Category mainCategory = categoryRepository.findById(req.getMainCategoryId()).orElse(null);
        Category subCategory = null;
        if(req.getSubCategoryId() != null) {
            subCategory = categoryRepository.findById(req.getSubCategoryId()).orElse(null);
        }
        Product product = Product.newOne(mainCategory, subCategory, req.getName(), req.getPrice(), req.getDetail(), req.getBrand(), req.getImageUrl(), req.getCreatedBy());
        return productRepository.save(product).toDTO();
    }

    @Transactional
    public ProductView updateProduct(ProductRequest req){
        Category mainCategory = categoryRepository.findById(req.getMainCategoryId()).orElse(null);
        Category subCategory = null;
        if(req.getSubCategoryId() != null) {
            categoryRepository.findById(req.getSubCategoryId()).orElse(null);
        }
        Product product = Product.updateProduct(req.getId(), mainCategory, subCategory, req.getName(), req.getPrice(), req.getDetail(), req.getBrand(), req.getImageUrl(), req.getUpdatedBy());
        return productRepository.save(product).toDTO();
    }

    @Transactional
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
