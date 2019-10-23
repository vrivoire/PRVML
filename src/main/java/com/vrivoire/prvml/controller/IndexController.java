package com.vrivoire.prvml.controller;

import com.vrivoire.prvml.model.Availability;
import com.vrivoire.prvml.model.Patient;
import com.vrivoire.prvml.model.Professional;
import com.vrivoire.prvml.service.BookingService;
import com.vrivoire.prvml.service.PatientService;
import com.vrivoire.prvml.service.ProfessionalService;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Vincent
 */
@Controller
public class IndexController {

    private static final Logger LOG = LogManager.getLogger(IndexController.class);

    @Autowired
    PatientService patientService;

    @Autowired
    ProfessionalService professionalService;

    @Autowired
    BookingService bookingService;

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String showIndex(Model model) {
        return "index";
    }

    /**
     *
     * @param patientUniqueId
     * @return
     */
    @RequestMapping(value = "/getPatient", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Patient> getPatient(@RequestParam(value = "patientUniqueId", required = true) String patientUniqueId) {
        Patient patient = patientService.findByUniqueId(patientUniqueId);
        return ResponseEntity.ok(patient);
    }

    /**
     *
     * @param appointementId
     * @return
     */
    @RequestMapping(value = "/getProfessionalForAppointment", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Professional> getProfessionalForAppointment(@RequestParam(value = "appointementId", required = true) Long appointementId) {
        Professional professional = professionalService.getProfessionalForAppointment(appointementId);
        return ResponseEntity.ok(professional);
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/getProfessionals", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Professional>> getProfessionals() {
        List<Professional> professionals = professionalService.loadAll();
        Collections.sort(professionals);
        return ResponseEntity.ok(professionals);
    }

    /**
     *
     * @param professionalId
     * @return
     */
    @RequestMapping(value = "/getAvailabilities", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Availability>> getAvailabilities(@RequestParam(value = "professionalId", required = true) Long professionalId) {
        Professional professional = professionalService.getProfessional(professionalId);
        List<Availability> availabilities = professional.getAvailabilities();
        Collections.sort(availabilities);
        return ResponseEntity.ok(availabilities);
    }

    /**
     *
     * @param availabilityId
     * @param professionalId
     * @param patientId
     * @return
     */
    @RequestMapping(value = "/bookAppointment", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> bookAppointment(
            @RequestParam(value = "availabilityId", required = true) Long availabilityId,
            @RequestParam(value = "professionalId", required = true) Long professionalId,
            @RequestParam(value = "patientId", required = true) Long patientId) {
        try {
            Set<ConstraintViolation<Object>> set = bookingService.bookAppointment(availabilityId, professionalId, patientId);
            if (set == null || set.isEmpty()) {
                LOG.info("Appointment created for patient id: " + patientId);
                return ResponseEntity.ok("Appointment created");
            } else {
                LOG.error("Could not add an appointement" + set);
                return ResponseEntity.badRequest().body(set);
            }
        } catch (Exception ex) {
            LOG.error("Could not add an appointement", ex);
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
