package com.snackhuborder.domain.order;


import com.snackhuborder.domain.utils.NumberUtils;
import com.snackhuborder.domain.validation.Error;
import com.snackhuborder.domain.validation.ValidationHandler;
import com.snackhuborder.domain.validation.Validator;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItemValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 255;
    public static final int NAME_MIN_LENGTH = 3;
    private final OrderItem item;

    protected OrderItemValidator(final OrderItem item, final ValidationHandler aHandler) {
        super(aHandler);
        this.item = item;
    }

    @Override
    public void validate() {
        checkNameConstraints();
        checkQuantityConstraints();
        checkCategoryConstraints();
        checkPriceConstraints();
    }

    private void checkCategoryConstraints() {
        OrderItemCategory category = this.item.getCategory();

        if (Objects.isNull(category)){
            this.validationHandler().append(new Error("'category' is required"));
        }
    }

    private void checkPriceConstraints() {
        BigDecimal price = this.item.getPrice();
        String fieldName = "price";

        if (Objects.isNull(price)){
            this.validationHandler().append(new Error("'"+ fieldName +"' is required"));
            return;
        }

        if (NumberUtils.isNegative(price)){
            this.validationHandler().append(new Error("'"+ fieldName +"' should not be negative"));
        }
    }

    private void checkQuantityConstraints() {
        Integer quantity = this.item.getQuantity();

        if (Objects.isNull(quantity) || quantity.compareTo(0) < 1) {
            this.validationHandler().append(new Error("'quantity' must be greater than zero"));
        }
    }

    private void checkNameConstraints() {
        String name = this.item.getName();
        String fieldName = "name";

        if (Objects.isNull(name)) {
            this.validationHandler().append(new Error("'"+ fieldName +"' is required"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("'"+ fieldName +"' should not be empty"));
            return;
        }

        final int length = name.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'"+ fieldName +"' must be between 3 and 255 characters"));
        }
    }
}
