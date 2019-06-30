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

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Assert;
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
public class BookingServiceTest {

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

    public BookingServiceTest() {
    }

    @Test
    public void book() {
        Clinic clinic = new Clinic("Le gros bobo");
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.of(new Patient("Jean", "Robin", clinic)));
        Mockito.when(professionalRepository.findById(2L)).thenReturn(Optional.of(new Professional("Hubert", "Salazard", clinic)));
        Mockito.when(appointmentRepository.findById(3L)).thenReturn(Optional.of(new Appointment(Timestamp.valueOf("2019-07-11 09:00:00"), Timestamp.valueOf("2019-07-11 09:30:00"))));
        Mockito.when(availabilityRepository.findById(4L)).thenReturn(Optional.of(new Availability(Timestamp.valueOf("2019-07-11 09:00:00"), Timestamp.valueOf("2019-07-11 09:30:00"))));

        Set<ConstraintViolation<Object>> set = null;
        try {
            set = bookingService.bookAppointment(4L, 2L, 1L);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
        System.out.println("Set=" + (set == null ? null : set));
        Assert.assertTrue(set.isEmpty());
    }
}
