package com.fullcycle.admin.catalogo.application.category.delete;

import com.fullcycle.admin.catalogo.domain.CategoryID;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;

import java.util.Objects;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase{
    private final CategoryGateway gateway;

    public DefaultDeleteCategoryUseCase(CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public void execute(String anIn) {

        final var anId = CategoryID.from(anIn);

        this.gateway.deleteById(anId);
    }

}
