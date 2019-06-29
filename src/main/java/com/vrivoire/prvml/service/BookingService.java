package com.vrivoire.prvml.service;

import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 *
 * @author Vincent
 */
public interface BookingService {

    Set<ConstraintViolation<Object>> addAppointment(Long availabilityId, Long professionalId, Long patientId);

}
