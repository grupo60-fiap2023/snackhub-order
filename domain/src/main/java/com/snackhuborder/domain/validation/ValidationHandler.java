package com.snackhuborder.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);

    List<Error> getErrors();

    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError() {
        return getErrors()
                .stream()
                .findFirst()
                .orElse(null);
    }

    interface Validation<T> {
        T validate();
    }
}