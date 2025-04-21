package org.conference_desks.common.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.conference_desks.reservation.ReservationRequest;


public class ReservationPeriodValidator implements ConstraintValidator<ValidReservationPeriod, ReservationRequest> {

    @Override
    public boolean isValid(ReservationRequest request, ConstraintValidatorContext context) {
        if (request.getStartTime() == null || request.getEndTime() == null) {
            return true; // inne adnotacje (np. @NotNull) to obsłużą
        }
        return request.getStartTime().isBefore(request.getEndTime());
    }
}

