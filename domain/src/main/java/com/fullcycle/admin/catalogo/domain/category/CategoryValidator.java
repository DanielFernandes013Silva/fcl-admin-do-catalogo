package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.Error;
import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;
import com.fullcycle.admin.catalogo.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;
    public CategoryValidator(final ValidationHandler handler, final Category aCategory) {
        super(handler);
        this.category = aCategory;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.category.getName();
        if (name == null){
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }
        if (name.isBlank()){
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }
        int length = name.trim().length();
        if (length < 3 || length > 255){
            this.validationHandler().append(new Error("'name' must between 3 and 255 characters"));

        }
    }
}
