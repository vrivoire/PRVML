package com.vrivoire.prvml.repositories;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Vincent
 */
public class Validator {

    private static final Logger LOG = LogManager.getLogger(Validator.class);

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
