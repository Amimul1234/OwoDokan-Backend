package com.shopKpr.adminCategories.repository.jpa;

import com.shopKpr.adminCategories.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
