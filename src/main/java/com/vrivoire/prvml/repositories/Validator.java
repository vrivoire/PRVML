package com.vrivoire.prvml.repositories;

import com.vrivoire.prvml.model.Booking;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.Interval;

/**
 *
 * @author Vincent
 */
public class Validator {

    private static final Logger LOG = LogManager.getLogger(Validator.class);

    public static boolean isInThePast(Booking booking) {
        return booking.getEndTime().getTime() < System.currentTimeMillis();
    }

    public static boolean isOverLaped(Booking booking1, Booking booking2) {
        Interval interval1 = new Interval(booking1.getStartTime().getTime(), booking1.getEndTime().getTime());
        Interval interval2 = new Interval(booking2.getStartTime().getTime(), booking2.getEndTime().getTime());
        return interval1.overlaps(interval2);
    }

    public static <T> Set<ConstraintViolation<T>> validate(T object) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        violations.forEach((violation) -> {
            LOG.error("Validation error for: " + object + "\n\t" + violations.size() + " " + violation.getMessage());
        });
        return violations;
    }
}
