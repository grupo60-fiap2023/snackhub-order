package com.snackhuborder.domain;


import com.snackhuborder.domain.validation.ValidationHandler;

public abstract class Entity<T extends Identifier> {

    protected final T id;

    protected Entity(final T id) {
        this.id = id;
    }

    public abstract void validate(ValidationHandler handler);

    public T getId() {
        return id;
    }
}
