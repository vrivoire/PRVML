package com.vrivoire.prvml.service;

import com.vrivoire.prvml.model.Availability;
import com.vrivoire.prvml.model.Patient;
import com.vrivoire.prvml.model.Professional;

import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 *
 * @author Vincent
 */
public interface BookingService {

    /**
     *
     * @param availability
     * @param professional
     * @param patient
     * @return
     * @throws Exception
     */
    public Set<ConstraintViolation<Object>> bookAppointment(Availability availability, Professional professional, Patient patient) throws Exception;

    /**
     *
     * @param availabilityId
     * @param professionalId
     * @param patientId
     * @return
     * @throws Exception
     */
    public Set<ConstraintViolation<Object>> bookAppointment(Long availabilityId, Long professionalId, Long patientId) throws Exception;

}
