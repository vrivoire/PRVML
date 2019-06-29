package com.vrivoire.prvml;

import com.vrivoire.prvml.model.Appointment;
import com.vrivoire.prvml.model.Availability;
import com.vrivoire.prvml.model.Clinic;
import com.vrivoire.prvml.model.Patient;
import com.vrivoire.prvml.model.Professional;
import com.vrivoire.prvml.repositories.AppointmentRepository;
import com.vrivoire.prvml.repositories.AvailabilityRepository;
import com.vrivoire.prvml.repositories.ClinicRepository;
import com.vrivoire.prvml.repositories.PatientRepository;
import com.vrivoire.prvml.repositories.ProfessionalRepository;
import com.vrivoire.prvml.service.BookingService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Main {

    private static final Logger LOG = LogManager.getLogger(Main.class);
    private static ClinicRepository clinicRepository = null;
    private static ProfessionalRepository professionalRepository = null;
    private static PatientRepository patientRepository = null;
    private static AppointmentRepository appointmentRepository = null;
    private static AvailabilityRepository availabilityRepository = null;
    private static BookingService bookingService = null;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        init(context);

        LOG.info("Server started.");
        LOG.info("You may go to the following URL: http://127.0.0.1:8080");
        LOG.info("H2 console URL: http://127.0.0.1:8080/h2-console  JDBC URL: jdbc:h2:mem:prvml or jdbc:h2:./database/prvml.db ((user: sa)");
    }

    private static void init(ConfigurableApplicationContext context) throws BeansException {

        clinicRepository = context.getBean(ClinicRepository.class);
        Clinic clinic = new Clinic("Clinique Auteuil");
        clinicRepository.saveAndFlush(clinic);

        professionalRepository = context.getBean(ProfessionalRepository.class);
        Professional professional1 = new Professional("Lucie", "Bazinet", clinic);
        Professional professional2 = new Professional("Claude", "Meunier", clinic);

        availabilityRepository = context.getBean(AvailabilityRepository.class);
        Availability availability = new Availability(Timestamp.valueOf("2019-07-01 09:00:00"), Timestamp.valueOf("2019-07-01 09:30:00"));
        availabilityRepository.saveAndFlush(availability);
        professional1.addAvailability(availability);

        availability = new Availability(Timestamp.valueOf("2019-07-03 14:00:00"), Timestamp.valueOf("2019-07-03 14:45:00"));
        availabilityRepository.saveAndFlush(availability);
        professional1.addAvailability(availability);

        availabilityRepository = context.getBean(AvailabilityRepository.class);
        availability = new Availability(Timestamp.valueOf("2019-07-02 10:30:00"), Timestamp.valueOf("2019-07-02 11:45:00"));
        availabilityRepository.saveAndFlush(availability);
        professional2.addAvailability(availability);

        availability = new Availability(Timestamp.valueOf("2019-07-01 09:00:00"), Timestamp.valueOf("2019-07-01 10:00:00"));
        availabilityRepository.saveAndFlush(availability);
        professional2.addAvailability(availability);

        patientRepository = context.getBean(PatientRepository.class);
        Patient patient = new Patient("Vincent", "Rivoire", clinic);

        appointmentRepository = context.getBean(AppointmentRepository.class);
        Appointment appointment = new Appointment(Timestamp.valueOf("2019-07-10 09:00:00"), Timestamp.valueOf("2019-07-10 09:30:00"));
        appointmentRepository.saveAndFlush(appointment);
        patient.addAppointment(appointment);
        professional1.addAppointment(appointment);
        patientRepository.saveAndFlush(patient);

        professionalRepository.saveAndFlush(professional1);
        professionalRepository.saveAndFlush(professional2);

        bookingService = context.getBean(BookingService.class);
        availability = new Availability(Timestamp.valueOf("2019-07-11 09:00:00"), Timestamp.valueOf("2019-07-11 09:30:00"));
        availabilityRepository.saveAndFlush(availability);
        professional2.addAvailability(availability);
        professionalRepository.saveAndFlush(professional2);
        Set<ConstraintViolation<Object>> set = bookingService.addAppointment(availability.getId(), professional2.getId(), patient.getId());
        if (set == null || set.isEmpty()) {
            LOG.info(set);
        }

        showData(clinic, professional1, professional2, patient);
    }

    public static ClinicRepository getClinicRepository() {
        return clinicRepository;
    }

    public static ProfessionalRepository getProfessionalRepository() {
        return professionalRepository;
    }

    public static PatientRepository getPatientRepository() {
        return patientRepository;
    }

    public static AppointmentRepository getAppointmentRepository() {
        return appointmentRepository;
    }

    public static AvailabilityRepository getAvailabilityRepository() {
        return availabilityRepository;
    }

    private static void showData(Clinic clinic, Professional professional1, Professional professional2, Patient patient) {
        LOG.info(clinic);
        LOG.info(professional1);
        LOG.info(professional2);
        LOG.info(patient);
        List<Availability> availabilities = availabilityRepository.findAll(new Sort(Direction.DESC, "startTime"));
        LOG.info(availabilities);
        List<Appointment> appointments = appointmentRepository.findAll(new Sort(Direction.DESC, "startTime"));
        LOG.info(appointments);
    }
}
