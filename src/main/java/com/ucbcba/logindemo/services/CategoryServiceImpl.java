package com.ucbcba.logindemo.services;

import com.ucbcba.logindemo.entities.Category;
import com.ucbcba.logindemo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    @Qualifier(value = "categoryRepository")
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public List<Category> findAll(){ return categoryRepository.findAll(); }

    @Override
    public Iterable<Category> listAllCategories() {
        Iterable<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public void create(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Category getById(Integer id){
        Optional<Category> category = categoryRepository.findById(id);
        return category.get();
    }

    @Override
    public Category findByName(String name){
        Category category = categoryRepository.findByName(name);
        return category;
    }

    @Override
    public void save(Category category){categoryRepository.save(category);}

    @Override
    public void deleteById(Integer id){categoryRepository.deleteById(id);}

}