package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.CategoryView;
import com.pjw.retry_view.enums.CategoryType;
import com.pjw.retry_view.entity.Category;
import com.pjw.retry_view.repositoryImpl.CategoryRepositoryImpl;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepositoryImpl categoryRepositoryImpl;

    public CategoryService(CategoryRepositoryImpl categoryRepositoryImpl) {
        this.categoryRepositoryImpl = categoryRepositoryImpl;
    }

    public List<CategoryView> getCategoryList(CategoryType type, Long groupId){
        return categoryRepositoryImpl.findByTypeAndGroupId(type, groupId).stream().map(Category::toDTO).toList();
    }

    @Transactional
    public CategoryView saveCategory(CategoryView req){
        return categoryRepositoryImpl.save(req.toEntity()).toDTO();
    }

    @Transactional
    public CategoryView updateCategory(CategoryView req){
        return categoryRepositoryImpl.save(req.toEntity()).toDTO();
    }

    @Transactional
    public void deleteCategory(Long id){
        categoryRepositoryImpl.deleteById(id);
    }
}
