package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.ProductView;
import com.pjw.retry_view.request.ProductRequest;
import com.pjw.retry_view.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "상품 정보 API 컨트롤러", description = "상품 정보 관리 API")
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "상품 목록 조회 API", description = "")
    @GetMapping
    public List<ProductView> getProductList(){
        return productService.getProductList();
    }

    @Operation(summary = "상품 상세 정보 조회 API", description = "")
    @GetMapping("/{id}")
    public ProductView getProduct(@PathVariable("id") Long id){
        return productService.getProduct(id);
    }

    @Operation(summary = "상품 정보 작성 API", description = "")
    @PostMapping
    public ProductView saveProduct(@RequestBody @Valid ProductRequest req){
        return productService.saveProduct(req);
    }

    @Operation(summary = "상품 정보 수정 API", description = "")
    @PutMapping
    public ProductView updateProduct(@RequestBody @Valid ProductRequest req){
        return productService.updateProduct(req);
    }

    @Operation(summary = "상품 정보 삭제 API", description = "")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
    }
}
