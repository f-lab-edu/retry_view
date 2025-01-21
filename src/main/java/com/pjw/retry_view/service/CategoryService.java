package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.CategoryDTO;
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

    public List<CategoryDTO> getCategoryList(CategoryType type, Long groupId){
        return categoryRepository.findByTypeAndGroupId(type, groupId).stream().map(Category::toDTO).toList();
    }

    @Transactional
    public CategoryDTO saveCategory(CategoryDTO req){
        return categoryRepository.save(req.toEntity()).toDTO();
    }

    @Transactional
    public CategoryDTO updateCategory(CategoryDTO req){
        return categoryRepository.save(req.toEntity()).toDTO();
    }

    @Transactional
    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}
