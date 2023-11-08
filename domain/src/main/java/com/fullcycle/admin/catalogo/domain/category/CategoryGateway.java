package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.CategoryID;
import com.fullcycle.admin.catalogo.domain.pagination.Pagination;

import java.util.Objects;
import java.util.Optional;

public interface CategoryGateway {

    Category create(Category aCategory);

    Category update(Category aCategory);

     void deleteById(CategoryID anId);

     Optional<Category> findById(CategoryID anId);

     Pagination<Category> findAll(CategorySearchQuery aQuery);

}
