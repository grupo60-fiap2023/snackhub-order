package com.snackhuborder.domain;


import com.snackhuborder.domain.validation.ValidationHandler;

public abstract class Entity<IdentifierType  extends Identifier> {

    protected final IdentifierType  id;

    protected Entity(final IdentifierType  id) {
        this.id = id;
    }

    public abstract void validate(ValidationHandler handler);

    public IdentifierType  getId() {
        return id;
    }
}
