package com.vrivoire.prvml.repositories;

import java.sql.Timestamp;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
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

    public static boolean isOverLaped(@NotNull Timestamp start1, @NotNull Timestamp end1, @NotNull Timestamp start2, @NotNull Timestamp end2) {
        Interval interval1 = new Interval(start1.getTime(), end1.getTime());
        Interval interval2 = new Interval(start2.getTime(), end2.getTime());
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
