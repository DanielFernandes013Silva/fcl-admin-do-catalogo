package com.fullcycle.admin.catalogo.application.category.retrieve.get;

import com.fullcycle.admin.catalogo.application.category.retrieve.CategoryOutput;
import com.fullcycle.admin.catalogo.domain.CategoryID;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.handler.Notification;
import com.fullcycle.admin.catalogo.domain.validation.Error;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private final CategoryGateway gateway;

    public DefaultGetCategoryByIdUseCase(final CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public CategoryOutput execute(String anIn) {

        final var anCategoryId = CategoryID.from(anIn);

        return this.gateway.findById(anCategoryId)
                .map(CategoryOutput::from)
                .orElseThrow(notFound(anCategoryId));

    }
    private static Supplier<DomainException> notFound(final CategoryID anId) {
        return () -> DomainException.with(new Error("Category with ID %s was not-found!".formatted(anId.getValue())));
    }
}
