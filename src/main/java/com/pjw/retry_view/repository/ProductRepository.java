package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findAll();
    public Optional<Product> findById(Long id);
    public Product save(Product product);
    public void deleteById(Long id);
}
