package com.vrivoire.prvml.service;

import com.vrivoire.prvml.model.Appointment;
import com.vrivoire.prvml.model.Availability;
import com.vrivoire.prvml.model.Patient;
import com.vrivoire.prvml.model.Professional;
import com.vrivoire.prvml.model.Validator;
import com.vrivoire.prvml.repositories.AppointmentRepository;
import com.vrivoire.prvml.repositories.AvailabilityRepository;
import com.vrivoire.prvml.repositories.PatientRepository;
import com.vrivoire.prvml.repositories.ProfessionalRepository;

import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vincent
 */
@Service
public class BookingServiceImpl implements BookingService {

    private static final Logger LOG = LogManager.getLogger(BookingServiceImpl.class);

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ProfessionalRepository professionalRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AvailabilityRepository availabilityRepository;

    /**
     *
     * @param availability
     * @param professional
     * @param patient
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Set<ConstraintViolation<Object>> bookAppointment(Availability availability, Professional professional, Patient patient) throws Exception {
        Appointment appointment = new Appointment(availability.getStartTime(), availability.getEndTime());
        Set<ConstraintViolation<Object>> validateAp = Validator.validate(appointment);
        if (!validateAp.isEmpty()) {
            return validateAp;
        }
        appointment = appointmentRepository.saveAndFlush(appointment);
        patient.addAppointment(appointment);
        Set<ConstraintViolation<Object>> validatePa = Validator.validate(patient);
        if (!validatePa.isEmpty()) {
            return validatePa;
        }

        patientRepository.saveAndFlush(patient);
        professional.addAppointment(appointment);
        Set<ConstraintViolation<Object>> validatePr = Validator.validate(professional);
        if (!validatePr.isEmpty()) {
            return validatePr;
        }
        professional.getAvailabilities().remove(availability);
        professionalRepository.saveAndFlush(professional);

        return null;

    }

    /**
     *
     * @param availabilityId
     * @param professionalId
     * @param patientId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Set<ConstraintViolation<Object>> bookAppointment(Long availabilityId, Long professionalId, Long patientId) throws Exception {

        Patient patient = patientRepository.findById(patientId).get();
        Professional professional = professionalRepository.findById(professionalId).get();
        Availability availability = availabilityRepository.findById(availabilityId).get();
        return bookAppointment(availability, professional, patient);
    }
}
