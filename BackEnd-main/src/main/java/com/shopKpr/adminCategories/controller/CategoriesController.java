package com.shopKpr.adminCategories.controller;

import com.shopKpr.adminCategories.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/admin")
public class CategoriesController {

    private final CategoryService categoryService;

    public CategoriesController( CategoryService categoryService ) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("addNewCategory/{categoryName}")
    public void addNewCategory( @PathVariable(name = "categoryName") String categoryName,
                                @RequestPart MultipartFile multipartFile ) {
        categoryService.addNewCategory(categoryName, multipartFile);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("changeCategoryImage/{categoryId}")
    public void changeCategoryImage( @PathVariable(name = "categoryId") Long categoryId,
                                     @RequestPart MultipartFile multipartFile ) {
        categoryService.changeCategoryImage(categoryId, multipartFile);
    }
}
