package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.CategoryView;
import com.pjw.retry_view.enums.CategoryType;
import com.pjw.retry_view.entity.Category;
import com.pjw.retry_view.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryView> getCategoryList(CategoryType type, Long groupId){
        return categoryRepository.findByTypeAndGroupId(type, groupId).stream().map(Category::toDTO).toList();
    }

    @Transactional
    public CategoryView saveCategory(CategoryView req){
        return categoryRepository.save(req.toEntity()).toDTO();
    }

    @Transactional
    public CategoryView updateCategory(CategoryView req){
        return categoryRepository.save(req.toEntity()).toDTO();
    }

    @Transactional
    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}
