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
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class IndexController {

    private static final Logger LOG = LogManager.getLogger(IndexController.class);

    @Autowired
    PatientService patientService;

    @Autowired
    ProfessionalService professionalService;

    @Autowired
    BookingService bookingService;

    @RequestMapping("/")
    public String showIndex(Model model) {
        return "index";
    }

    @RequestMapping(value = "/getPatient", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Patient> getPatient(@RequestParam(value = "patientUniqueId", required = true) String patientUniqueId) {
        Patient patient = patientService.findByUniqueId(patientUniqueId);
        return ResponseEntity.ok(patient);
    }

    @RequestMapping(value = "/getProfessionals", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Professional>> getProfessionals() {
        List<Professional> professionals = professionalService.loadAll();
        Collections.sort(professionals);
        return ResponseEntity.ok(professionals);
    }

    @RequestMapping(value = "/getAvailabilities", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Availability>> getAvailabilities(@RequestParam(value = "professionalId", required = true) Long professionalId) {
        Professional professional = professionalService.getProfessional(professionalId);
        List<Availability> availabilities = professional.getAvailabilities();
        Collections.sort(availabilities);
        return ResponseEntity.ok(availabilities);
    }

    @RequestMapping(value = "/addAppointment", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public RedirectView addAppointment(
            @RequestParam(value = "availabilityId", required = true) Long availabilityId,
            @RequestParam(value = "professionalId", required = true) Long professionalId,
            @RequestParam(value = "patientId", required = true) Long patientId) {

        Set<ConstraintViolation<Object>> set = bookingService.addAppointment(availabilityId, professionalId, patientId);

        if (set == null || set.isEmpty()) {
            LOG.info("Appointment created for patient id: " + patientId);
            return new RedirectView("/", true);
        } else {
            return new RedirectView("/error", true);
        }
    }
}
