package com.fullcycle.admin.catalogo.application.category.update;

import com.fullcycle.admin.catalogo.domain.CategoryID;
import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.handler.Notification;
import com.fullcycle.admin.catalogo.domain.validation.Error;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

import static io.vavr.API.Left;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    final CategoryGateway gateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand aCommand) {

        final var anId = CategoryID.from(aCommand.id());
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var aIsActive = aCommand.isActive();

        final var aCategory = this.gateway.findById(anId).
                orElseThrow(notFound(anId));

        Notification notification = Notification.create();

        aCategory.update(aName, aDescription, aIsActive)
                .validate(notification);
        return notification.hasErrors() ? Left(notification) : update(aCategory);
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category aCategory) {
        return API.Try(() -> this.gateway.update(aCategory))
                .toEither()
                .bimap(Notification :: create, UpdateCategoryOutput ::from);
    }

    private static Supplier<DomainException> notFound(final CategoryID anId) {
        return () -> DomainException.with(new Error("Category with ID %s was not-found!".formatted(anId.getValue())));
    }
}
