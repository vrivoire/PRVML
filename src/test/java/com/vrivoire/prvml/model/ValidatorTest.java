package com.vrivoire.prvml.model;

import java.sql.Timestamp;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class ValidatorTest {

    private Clinic clinic;
    private Patient patient;
    private Appointment appointment1;
    private Appointment appointment2;
    private Appointment appointment3;
    private Appointment appointment4;

    /**
     *
     */
    public ValidatorTest() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
        clinic = new Clinic("Le gros bobo");
        patient = new Patient("Jean", "Robin", clinic);
        appointment1 = new Appointment(Timestamp.valueOf("2020-07-10 09:00:00"), Timestamp.valueOf("2020-07-10 09:30:00"));
        appointment2 = new Appointment(Timestamp.valueOf("2020-07-10 09:15:00"), Timestamp.valueOf("2020-07-10 09:30:00"));
        appointment3 = new Appointment(Timestamp.valueOf("2020-07-11 09:15:00"), Timestamp.valueOf("2020-07-11 09:30:00"));
        appointment4 = new Appointment(Timestamp.valueOf("2018-07-11 09:15:00"), Timestamp.valueOf("2018-07-11 09:30:00"));
    }

    /**
     *
     */
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

    /**
     *
     */
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

    /**
     *
     */
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

    /**
     *
     */
    @Test
    public void validateValidate1() {
        Set<ConstraintViolation<Patient>> validate = Validator.validate(patient);
        Assert.assertNotNull(validate);
        Assert.assertTrue(validate.isEmpty());
    }

    /**
     *
     */
    @Test
    public void validateValidate2() {
        Set<ConstraintViolation<Patient>> validate = Validator.validate(new Patient("Jean", "Robin", null));
        System.out.println(validate);
        Assert.assertNotNull(validate);
        Assert.assertFalse(validate.isEmpty());
    }
}
