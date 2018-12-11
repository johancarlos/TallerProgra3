package com.ucbcba.logindemo.repositories;

import com.ucbcba.logindemo.entities.Category;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("select u from Category u where u.name = :#{#category}")
    List<Category> findCategoryByName(@Param("category") String category);
    Category findByName(String name);
}