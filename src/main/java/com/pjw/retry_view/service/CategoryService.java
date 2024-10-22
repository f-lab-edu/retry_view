package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.CategoryDTO;
import com.pjw.retry_view.dto.CategoryType;
import com.pjw.retry_view.entity.Category;
import com.pjw.retry_view.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getCategoryList(CategoryType type, Long groupId){
        return categoryRepository.findByTypeAndGroupId(type, groupId).stream().map(Category::toDTO).toList();
    }

    public CategoryDTO saveCategory(CategoryDTO req){
        return categoryRepository.save(req.toEntity()).toDTO();
    }

    public CategoryDTO updateCategory(CategoryDTO req){
        return categoryRepository.save(req.toEntity()).toDTO();
    }

    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}
