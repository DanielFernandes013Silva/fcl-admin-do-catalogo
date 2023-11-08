package com.fullcycle.admin.catalogo.application.category.update;

import com.fullcycle.admin.catalogo.domain.CategoryID;
import com.fullcycle.admin.catalogo.domain.category.Category;

public record UpdateCategoryOutput(
        CategoryID id
) {

     public static UpdateCategoryOutput from(final Category aCategory) {
         return new UpdateCategoryOutput(aCategory.getId());
     }
}
