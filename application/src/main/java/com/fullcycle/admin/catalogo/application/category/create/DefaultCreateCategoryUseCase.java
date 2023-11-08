package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase{

    public DefaultCreateCategoryUseCase(final CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    final CategoryGateway gateway;
    @Override
    public Either<Notification, CreateCategoryOutput> execute(final CreateCategoryCommand aCommand) {

        final var aCategory = Category.newCategory(aCommand.name(), aCommand.description(), aCommand.isActive());
        final var notification = Notification.create();

        aCategory.validate(notification);

        return notification.hasErrors() ? Left(notification) : create(aCategory);

    }
    private Either<Notification, CreateCategoryOutput> create(final Category aCategory) {

        return Try(() -> this.gateway.create(aCategory))
                .toEither()
                .bimap(Notification::create, CreateCategoryOutput::from);
    }
}
