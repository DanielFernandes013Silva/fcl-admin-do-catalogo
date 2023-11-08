package com.fullcycle.admin.catalogo.application.category.retrieve.list;

import com.fullcycle.admin.catalogo.application.category.retrieve.CategoryOutput;
import com.fullcycle.admin.catalogo.domain.CategoryID;
import com.fullcycle.admin.catalogo.domain.category.Category;

import java.time.Instant;

public record CategoryListOutput(

        CategoryID id,
        String name,
        String description,
        boolean isActive,
        Instant createdAt,
        Instant deletedAt
) {
    public static CategoryListOutput from(final Category aCategoryOutput){

        return new CategoryListOutput(
                aCategoryOutput.getId(),
                aCategoryOutput.getName(),
                aCategoryOutput.getDescription(),
                aCategoryOutput.isActive(),
                aCategoryOutput.getCreatedAt(),
                aCategoryOutput.getDeletedAt()
                );
    }
}
