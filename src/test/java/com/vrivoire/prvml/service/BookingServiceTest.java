package com.vrivoire.prvml.service;

import com.vrivoire.prvml.model.Availability;
import com.vrivoire.prvml.model.Clinic;
import com.vrivoire.prvml.model.Patient;
import com.vrivoire.prvml.model.Professional;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 * @author Vincent
 */
@SpringBootTest
@ActiveProfiles("test")
//@ExtendWith(SpringJUnit4ClassRunner.class)
public class BookingServiceTest {

	private Clinic clinic;
	private Patient patient;
	private Professional professional;
	private Availability availability;

	@Autowired
	BookingService bookingService;

	/**
	 *
	 */
	public BookingServiceTest() {
	}

	/**
	 *
	 */
	@BeforeEach
	public void setUp() {
		clinic = new Clinic("Le gros bobo");
		patient = new Patient("Jean", "Robin", clinic);
		professional = new Professional("Hubert", "Salazard", clinic);
		availability = new Availability(Timestamp.valueOf("2020-07-11 09:00:00"), Timestamp.valueOf("2020-07-11 09:30:00"));
	}

	/**
	 *
	 */
	@Test
	public void bookAppointment1() {
		try {
			Mockito.when(bookingService.bookAppointment(availability, professional, patient)).thenReturn(new HashSet<>());
		} catch (Exception ex) {
		}

		Set<ConstraintViolation<Object>> set = null;
		try {
			set = bookingService.bookAppointment(availability, professional, patient);
		} catch (Exception ex) {
			Assertions.fail(ex.getMessage());
		}

		Assertions.assertNotNull(set);
		Assertions.assertTrue(set.isEmpty());
	}

	/**
	 *
	 */
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

		Assertions.assertNull(set);
	}
}
