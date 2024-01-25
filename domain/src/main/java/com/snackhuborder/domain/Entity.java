package com.snackhuborder.domain;


import com.snackhuborder.domain.validation.ValidationHandler;

public abstract class Entity<ID extends Identifier> {

    protected final ID id;

    protected Entity(final ID id) {
        this.id = id;
    }

    public abstract void validate(ValidationHandler handler);

    public ID getId() {
        return id;
    }
}
