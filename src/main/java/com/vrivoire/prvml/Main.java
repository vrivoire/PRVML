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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author Vincent
 */
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

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
            init(context);

            LOG.info("Server started.");
            LOG.info("You may go to the following URL: http://127.0.0.1:8080");
            LOG.info("H2 console URL: http://127.0.0.1:8080/h2-console  JDBC URL: jdbc:h2:mem:prvml or jdbc:h2:./database/prvml.db ((user: sa)");
            LOG.info("---------------------------------------------");
        } catch (Exception ex) {
            LOG.fatal(ex.getMessage(), ex);
            System.exit(-1);
        }
    }

    /**
     *
     * @param context
     * @throws Exception
     */
    public static void init(ConfigurableApplicationContext context) throws Exception {

        try {
            clinicRepository = context.getBean(ClinicRepository.class);
            Clinic clinic = new Clinic("Clinique Auteuil");
            clinicRepository.saveAndFlush(clinic);

            professionalRepository = context.getBean(ProfessionalRepository.class);
            Professional professional1 = new Professional("Lucie", "Bazinet", clinic);
            Professional professional2 = new Professional("Claude", "Meunier", clinic);

            availabilityRepository = context.getBean(AvailabilityRepository.class);
            Availability availability = new Availability(Timestamp.valueOf("2020-09-01 09:00:00"), Timestamp.valueOf("2020-09-01 09:30:00"));
            availabilityRepository.saveAndFlush(availability);
            professional1.addAvailability(availability);

            availability = new Availability(Timestamp.valueOf("2020-09-02 14:00:00"), Timestamp.valueOf("2020-09-02 14:45:00"));
            availabilityRepository.saveAndFlush(availability);
            professional1.addAvailability(availability);

            availabilityRepository = context.getBean(AvailabilityRepository.class);
            availability = new Availability(Timestamp.valueOf("2020-09-03 10:30:00"), Timestamp.valueOf("2020-09-03 11:45:00"));
            availabilityRepository.saveAndFlush(availability);
            professional2.addAvailability(availability);

            availability = new Availability(Timestamp.valueOf("2020-09-04 09:00:00"), Timestamp.valueOf("2020-09-04 10:00:00"));
            availabilityRepository.saveAndFlush(availability);
            professional2.addAvailability(availability);
            professionalRepository.saveAndFlush(professional2);

            patientRepository = context.getBean(PatientRepository.class);
            Patient patient = new Patient("Vincent", "Rivoire", clinic);
            patientRepository.saveAndFlush(patient);

            appointmentRepository = context.getBean(AppointmentRepository.class);
            Appointment appointment = new Appointment(Timestamp.valueOf("2020-09-10 09:00:00"), Timestamp.valueOf("2020-09-10 09:30:00"));
            appointmentRepository.saveAndFlush(appointment);
            patient.addAppointment(appointment);
            patientRepository.saveAndFlush(patient);
            professional1.addAppointment(appointment);
            professionalRepository.saveAndFlush(professional1);

            bookingService = context.getBean(BookingService.class);
            availability = new Availability(Timestamp.valueOf("2020-07-11 09:00:00"), Timestamp.valueOf("2020-07-11 09:30:00"));
            availabilityRepository.saveAndFlush(availability);
            professional2.addAvailability(availability);
            professionalRepository.saveAndFlush(professional2);
            Set<ConstraintViolation<Object>> set = bookingService.bookAppointment(availability.getId(), professional2.getId(), patient.getId());
            if (set != null && !set.isEmpty()) {
                LOG.info(set);
            }

            showData(clinic, professional1, professional2, patient);
        } catch (Exception ex) {
            LOG.error("Failed to create some data.", ex);
            throw ex;
        }
    }

    /**
     *
     * @return
     */
    public static ClinicRepository getClinicRepository() {
        return clinicRepository;
    }

    /**
     *
     * @return
     */
    public static ProfessionalRepository getProfessionalRepository() {
        return professionalRepository;
    }

    /**
     *
     * @return
     */
    public static PatientRepository getPatientRepository() {
        return patientRepository;
    }

    /**
     *
     * @return
     */
    public static AppointmentRepository getAppointmentRepository() {
        return appointmentRepository;
    }

    /**
     *
     * @return
     */
    public static AvailabilityRepository getAvailabilityRepository() {
        return availabilityRepository;
    }

    private static void showData(Clinic clinic, Professional professional1, Professional professional2, Patient patient) {
        Sort sort = Sort.by(Direction.DESC, "startTime");
        List<Availability> availabilities = availabilityRepository.findAll(sort);
        List<Appointment> appointments = appointmentRepository.findAll(sort);
        LOG.info("---------------------------------------------");
        LOG.info(clinic);
        LOG.info(professional1);
        LOG.info(professional2);
        LOG.info(patient);
        LOG.info(availabilities);
        LOG.info(appointments);
        LOG.info("---------------------------------------------");
    }
}
