package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.Category;
import com.pjw.retry_view.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> findAll();
    public List<Category> findByTypeAndGroupId(CategoryType type, Long groupId);
    public Optional<Category> findById(Long id);
    public Category save(Category category);
    public void deleteById(Long id);
}
