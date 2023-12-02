package com.snackhuborder.domain.order;


import com.snackhuborder.domain.validation.Error;
import com.snackhuborder.domain.validation.ValidationHandler;
import com.snackhuborder.domain.validation.Validator;

import java.util.Objects;

public class OrderValidator extends Validator {

    public static final int OBSERVATION_MAX_LENGTH = 255;

    public static final int TICKET_MAX_LENGTH = 50;
    public static final int MIN_LENGTH = 1;

    private final Order order;

    protected OrderValidator(final Order order, final ValidationHandler aHandler) {
        super(aHandler);
        this.order = order;
    }

    @Override
    public void validate() {
        checkOrderIdentifierConstraints();
        checkObservationConstraints();
        checkItemsOrderConstraints();
    }

    private void checkItemsOrderConstraints() {
        if (Objects.isNull(this.order.getItems()) || this.order.getItems().isEmpty()) {
            this.validationHandler().append(new Error("The order is necessary almost one item"));
        }
    }

    private void checkObservationConstraints() {
        String observation = this.order.getObservation();
        if (!Objects.isNull(observation) && !observation.isBlank()) {
            final int length = observation.trim().length();
            if (length > OBSERVATION_MAX_LENGTH || length < MIN_LENGTH) {
                this.validationHandler().append(new Error("'observation' must be between 1 and 255 characters"));
            }
        }
    }

    private void checkOrderIdentifierConstraints() {
        String orderIdentifier = this.order.getOrderIdentifier();
        var fieldName = "orderIdentifier";

        if (Objects.isNull(orderIdentifier) || orderIdentifier.isBlank()){
            this.validationHandler().append(new Error("'"+ fieldName +"' is required"));
            return;
        }

        final int length = orderIdentifier.trim().length();
        if (length > TICKET_MAX_LENGTH || length < MIN_LENGTH) {
            this.validationHandler().append(new Error("'orderIdentifier' must be between 1 and 50 characters"));
        }

    }
}
