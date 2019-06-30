package com.vrivoire.prvml.service;

import com.vrivoire.prvml.model.Appointment;
import com.vrivoire.prvml.model.Availability;
import com.vrivoire.prvml.model.Clinic;
import com.vrivoire.prvml.model.Patient;
import com.vrivoire.prvml.model.Professional;
import com.vrivoire.prvml.repositories.AppointmentRepository;
import com.vrivoire.prvml.repositories.AvailabilityRepository;
import com.vrivoire.prvml.repositories.PatientRepository;
import com.vrivoire.prvml.repositories.ProfessionalRepository;
import com.vrivoire.prvml.repositories.Validator;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Vincent
 */
@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class PrvmlTest {

    private Clinic clinic;
    private Patient patient;
    private Professional professional;
    private Availability availability;
    private Appointment appointment1;
    private Appointment appointment2;
    private Appointment appointment3;
    private Appointment appointment4;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ProfessionalRepository professionalRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AvailabilityRepository availabilityRepository;

    @Autowired
    BookingService bookingService;

    public PrvmlTest() {
    }

    @Before
    public void setUp() {
        clinic = new Clinic("Le gros bobo");
        patient = new Patient("Jean", "Robin", clinic);
        appointment1 = new Appointment(Timestamp.valueOf("2019-07-10 09:00:00"), Timestamp.valueOf("2019-07-10 09:30:00"));
        appointment2 = new Appointment(Timestamp.valueOf("2019-07-10 09:15:00"), Timestamp.valueOf("2019-07-10 09:30:00"));
        appointment3 = new Appointment(Timestamp.valueOf("2019-07-11 09:15:00"), Timestamp.valueOf("2019-07-11 09:30:00"));
        appointment4 = new Appointment(Timestamp.valueOf("2018-07-11 09:15:00"), Timestamp.valueOf("2018-07-11 09:30:00"));
        professional = new Professional("Hubert", "Salazard", clinic);
        availability = new Availability(Timestamp.valueOf("2019-07-11 09:00:00"), Timestamp.valueOf("2019-07-11 09:30:00"));
    }

    @Test
    public void validatePast() {

        try {
            patient.addAppointment(appointment4);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assert.assertNotNull(ex);
            return;

        }
        Assert.fail();
    }

    @Test
    public void validateOverlap1() {

        try {
            patient.addAppointment(appointment1);
            patient.addAppointment(appointment2);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assert.assertNotNull(ex);
            return;

        }
        Assert.fail();
    }

    @Test
    public void validateOverlap2() {
        try {
            patient.addAppointment(appointment1);
            patient.addAppointment(appointment3);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assert.fail();
            return;

        }
        Assert.assertNull(null);
    }

    @Test
    public void validateValidate1() {
        Set<ConstraintViolation<Patient>> validate = Validator.validate(patient);
        Assert.assertNotNull(validate);
        Assert.assertTrue(validate.isEmpty());
    }

    @Test
    public void validateValidate2() {
        Set<ConstraintViolation<Patient>> validate = Validator.validate(new Patient("Jean", "Robin", null));
        System.out.println(validate);
        Assert.assertNotNull(validate);
        Assert.assertFalse(validate.isEmpty());
    }

    @Test
    public void bookAppointment1() {
        try {
            Mockito.when(bookingService.bookAppointment(availability, professional, patient)).thenReturn(new HashSet<>());
        } catch (Exception ex) {
        }

        Set<ConstraintViolation<Object>> set = null;
        try {
            set = bookingService.bookAppointment(availability, professional, patient);
            System.out.println("Set=" + set);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }

        Assert.assertNotNull(set);
        Assert.assertTrue(set.isEmpty());
    }

    @Test
    public void bookAppointment2() {
        try {
            Mockito.when(bookingService.bookAppointment(availability, null, patient)).thenThrow(new Exception());
        } catch (Exception ex) {
        }

        Set<ConstraintViolation<Object>> set = null;
        try {
            set = bookingService.bookAppointment(availability, null, patient);
        } catch (Exception ex) {
        }

        Assert.assertNull(set);
    }
}
