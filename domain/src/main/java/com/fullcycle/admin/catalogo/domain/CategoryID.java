package com.fullcycle.admin.catalogo.domain;

import java.util.Objects;
import java.util.UUID;

public class CategoryID extends Identifier {

    private final String value;

    public CategoryID(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static CategoryID unique() {
        return new CategoryID(UUID.randomUUID().toString().toLowerCase());
    }
    public static CategoryID from(final String anId) {
        return new CategoryID(anId.toString().toLowerCase());
    }
    public static CategoryID from(final UUID anId) {
        return new CategoryID(anId.toString().toLowerCase());
    }
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CategoryID that = (CategoryID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
