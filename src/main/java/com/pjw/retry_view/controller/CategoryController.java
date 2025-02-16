package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.CategoryView;
import com.pjw.retry_view.enums.CategoryType;
import com.pjw.retry_view.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "카테고리 관리 API 컨트롤러", description = "카테고리 관리 API")
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "카테고리 목록 조회 API", description = "")
    @GetMapping
    public List<CategoryView> getCategoryList(@RequestParam(name = "type", required = true)CategoryType type, @RequestParam(name = "groupId", required = false)Long groupId){
        return categoryService.getCategoryList(type, groupId);
    }

    @Operation(summary = "카테고리 추가 API", description = "")
    @PostMapping
    public CategoryView addCategory(@RequestBody @Valid CategoryView categoryReq){
        return categoryService.saveCategory(categoryReq);
    }

    @Operation(summary = "카테고리 수정 API", description = "")
    @PutMapping
    public CategoryView updateCategory(@RequestBody @Valid CategoryView categoryReq){
        return categoryService.updateCategory(categoryReq);
    }

    @Operation(summary = "카테고리 삭제 API", description = "")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable(name = "id") Long id){
        categoryService.deleteCategory(id);
    }
}
