package com.musinsa.subject.repository;

import com.musinsa.subject.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByOrderByCategoryId();

    Optional<Category> findFirstByCategoryName(String categoryName);

}
