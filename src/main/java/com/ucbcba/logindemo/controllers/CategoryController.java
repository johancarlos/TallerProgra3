package com.ucbcba.logindemo.controllers;
import com.ucbcba.logindemo.entities.Category;
import com.ucbcba.logindemo.services.CategoryService;
import com.ucbcba.logindemo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;


@Controller
public class CategoryController {

    CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }



    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String list(Model model){
        Iterable<Category> categoryList = categoryService.listAllCategories();
        model.addAttribute("categoryList",categoryList);
        return "list_categories";
    }

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public String save(@ModelAttribute("post") Category category, Model model)
    {
        categoryService.save(category);
        return "redirect:/list_categories";
    }

   /* @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createCategories(Model model){
        model.addAttribute("category",new Category());
        return "createCategories";
    }*/

    @RequestMapping(value = "/createCategory", method = RequestMethod.POST)
    public String createCategories(Category category, BindingResult result,Model model){
        if(result.hasErrors()){
            model.addAttribute("category",new Category());
            return "createCategory";
        }
        categoryService.save(category);
        return "redirect:/index";
    }
    @RequestMapping(value = "/createCategoryForm", method = RequestMethod.GET)
    public String createCategoriesForm(Model model){
        return "createCategory";
    }
    /*
    @GetMapping("/edit/{id}")
    public String edit_Category(@PathVariable Integer id, Model model){
        Category category=categoryService.getById(id);
        model.addAttribute("category",new Category());
        return "create_Category";
    }*/

    @RequestMapping(value = "categories/editCategory", method = RequestMethod.POST)
    public String createCategories1(Category category, BindingResult result,Model model){
        Category newCategory = categoryService.getById(category.getId());
        newCategory.setName(category.getName());
        categoryService.save(newCategory);
        return "redirect:/index";
    }

    @RequestMapping(value = "categories/edit/{id}", method = RequestMethod.GET)
    public String editCategory(@PathVariable Integer id, Model model) {
        Category category = categoryService.getById(id);
        model.addAttribute("categoryhtml",category);
        return "editCategory";
    }

    @RequestMapping("categories/delete/{id}")
    public String delete(@PathVariable Integer id, Model model){
        categoryService.deleteById(id);
        return "redirect:/index";
    }

    @RequestMapping(value="/category/{nombrecat}",method = RequestMethod.GET)
    public String like(@PathVariable String nombrecat, Model model){
        Category category= categoryService.findByName(nombrecat);
        model.addAttribute("category", category);
        return "categoryPosts";
    }
}