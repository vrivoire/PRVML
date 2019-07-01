package com.vrivoire.prvml.service;

import com.vrivoire.prvml.model.Patient;
import com.vrivoire.prvml.repositories.PatientRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vincent
 */
@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    /**
     *
     * @return
     */
    @Override
    public List<Patient> loadAll() {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "uniqueId").ignoreCase();
        Sort.Order[] orders = new Sort.Order[1];
        orders[0] = order;
        Sort sort = Sort.by(orders);
        return patientRepository.findAll(sort);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Patient getPatient(Long id) {
        Optional<Patient> optional = patientRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    /**
     *
     * @param uniqueId
     * @return
     */
    @Override
    public Patient findByUniqueId(String uniqueId) {
        Patient patient = patientRepository.findByUniqueId(uniqueId);
        return patient;
    }

}
