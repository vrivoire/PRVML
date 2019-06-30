package com.vrivoire.prvml.service;

import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 *
 * @author Vincent
 */
public interface BookingService {

    Set<ConstraintViolation<Object>> bookAppointment(Long availabilityId, Long professionalId, Long patientId) throws Exception;

}
