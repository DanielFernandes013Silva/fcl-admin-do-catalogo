package com.fullcycle.admin.catalogo.application.category.retrieve.list;

import com.fullcycle.admin.catalogo.application.category.retrieve.CategoryOutput;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategorySearchQuery;
import com.fullcycle.admin.catalogo.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase{

    private final CategoryGateway gateway;

    public DefaultListCategoriesUseCase(final CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(final CategorySearchQuery aQuery) {

        return this.gateway.findAll(aQuery)
                .map(CategoryListOutput::from);

    }
}
