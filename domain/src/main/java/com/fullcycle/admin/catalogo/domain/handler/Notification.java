package com.fullcycle.admin.catalogo.domain.handler;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.Error;
import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {
    private List<Error> errors;

    public Notification(final List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create(){
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Error anError) {
        return new Notification(new ArrayList<>()).append(anError);
    }

    public static Notification create(final Throwable t) {
        return create(new Error(t.getMessage()));
    }
    @Override
    public Notification append(final Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public Notification append(final ValidationHandler anHandler) {
        this.errors.addAll(anHandler.getErrors());
        return this;
    }

    @Override
    public Notification validate(final Validation aValidation) {

        try {
            aValidation.validate();
        } catch (final DomainException e) {
           this.errors.addAll(e.getErrors());
        } catch (Throwable e){
            this.errors.add(new Error(e.getMessage()));
        }
        return this;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }

    @Override
    public boolean hasErrors() {
        return ValidationHandler.super.hasErrors();
    }
}
