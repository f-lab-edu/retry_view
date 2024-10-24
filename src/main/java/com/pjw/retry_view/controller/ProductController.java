package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.ProductDTO;
import com.pjw.retry_view.request.ProductRequest;
import com.pjw.retry_view.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getProductList(){
        return productService.getProductList();
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable("id") Long id){
        return productService.getProduct(id);
    }

    @PostMapping
    public ProductDTO saveProduct(@RequestBody @Valid ProductRequest req){
        return productService.saveProduct(req);
    }

    @PutMapping
    public ProductDTO updateProduct(@RequestBody @Valid ProductRequest req){
        return productService.updateProduct(req);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
    }
}
