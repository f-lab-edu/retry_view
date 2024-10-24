package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.CategoryDTO;
import com.pjw.retry_view.dto.CategoryType;
import com.pjw.retry_view.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDTO> getCategoryList(@RequestParam(name = "type")CategoryType type, @RequestParam(name = "groupId")Long groupId){
        return categoryService.getCategoryList(type, groupId);
    }

    @PostMapping
    public CategoryDTO addCategory(@RequestBody @Valid CategoryDTO categoryReq){
        return categoryService.saveCategory(categoryReq);
    }

    @PutMapping
    public CategoryDTO updateCategory(@RequestBody @Valid CategoryDTO categoryReq){
        return categoryService.updateCategory(categoryReq);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable(name = "id") Long id){
        categoryService.deleteCategory(id);
    }
}
