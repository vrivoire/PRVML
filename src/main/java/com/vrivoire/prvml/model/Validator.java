package com.vrivoire.prvml.model;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.Interval;

/**
 *
 * @author Vincent
 */
public class Validator {

    private static final Logger LOG = LogManager.getLogger(Validator.class);

    /**
     *
     * @param booking
     * @param bookings
     * @param caller
     * @throws ValidationException
     */
    public static void validateBookings(@NotNull Booking booking, @NotNull List<?> bookings, @NotNull Object caller) throws ValidationException {
        if (bookings.contains(booking)) {
            LOG.error("The time slot is already taken for: " + booking + " and " + caller);
            throw new ValidationException("The time slot is already taken");
        }
        List<Booking> reservations = (List<Booking>) bookings;
        reservations.stream().forEach((booking1) -> {
            if (isOverLaped(booking, booking1)) {
                LOG.error("There is an overlap time slot for: " + booking + " and " + caller);
                throw new ValidationException("There is an overlap time slot ");
            }
        });
        if (isInThePast(booking)) {
            LOG.error("The time slot is in the past: " + booking);
            throw new ValidationException("The time slot is in the past");
        }
    }

    /**
     *
     * @param <T>
     * @param object
     * @return
     */
    public static <T> Set<ConstraintViolation<T>> validate(T object) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        violations.forEach((violation) -> {
            LOG.error("Validation error for: " + object + "\n\t" + violations.size() + " " + violation.getMessage());
        });
        return violations;
    }

    private static boolean isInThePast(Booking booking) {
        if (booking.getEndTime().getTime() < System.currentTimeMillis());
        return booking.getEndTime().getTime() < System.currentTimeMillis();
    }

    private static boolean isOverLaped(Booking booking1, Booking booking2) {
        Interval interval1 = new Interval(booking1.getStartTime().getTime(), booking1.getEndTime().getTime());
        Interval interval2 = new Interval(booking2.getStartTime().getTime(), booking2.getEndTime().getTime());
        return interval1.overlaps(interval2);
    }
}
