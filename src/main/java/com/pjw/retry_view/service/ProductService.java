package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.ProductDTO;
import com.pjw.retry_view.entity.Product;
import com.pjw.retry_view.repository.ProductRepository;
import com.pjw.retry_view.request.ProductRequest;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getProductList(){
        return productRepository.findAll().stream().map(Product::toDTO).toList();
    }

    public ProductDTO getProduct(Long id){
        return productRepository.findById(id).orElseThrow(ResolutionException::new).toDTO();
    }

    public ProductDTO saveProduct(ProductRequest req){
        return productRepository.save(Product.newProductFromReq(req)).toDTO();
    }

    public ProductDTO updateProduct(ProductRequest req){
        return productRepository.save(Product.updateProductFromReq(req)).toDTO();
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
