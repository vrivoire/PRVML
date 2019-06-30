package com.vrivoire.prvml.service;

import com.vrivoire.prvml.model.Appointment;
import com.vrivoire.prvml.model.Professional;
import com.vrivoire.prvml.repositories.AppointmentRepository;
import com.vrivoire.prvml.repositories.ProfessionalRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProfessionalServiceImpl implements ProfessionalService {

    @Autowired
    private ProfessionalRepository professionalRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<Professional> loadAll() {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "uniqueId").ignoreCase();
        Sort.Order[] orders = new Sort.Order[1];
        orders[0] = order;
        Sort sort = Sort.by(orders);
        return professionalRepository.findAll(sort);
    }

    @Override
    public Professional getProfessional(Long id) {
        Optional<Professional> optional = professionalRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public Professional findByUniqueId(String uniqueId) {
        Professional professional = professionalRepository.findByUniqueId(uniqueId);
        return professional;
    }

    @Override
    public Professional getProfessionalForAppointment(Long appointementId) {
        Appointment appointment = appointmentRepository.findById(appointementId).get();
        if (appointment != null) {
            List<Professional> professionals = professionalRepository.findAll();
            for (Professional professional : professionals) {
                List<Appointment> appointments = professional.getAppointments();
                if (appointments.contains(appointment)) {
                    return professional;
                }
            }
        }
        return null;
    }
}
