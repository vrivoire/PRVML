package com.vrivoire.prvml.service;

import com.vrivoire.prvml.model.Patient;

import java.util.List;

public interface PatientService {

    List<Patient> loadAll();

    Patient getPatient(Long id);

    Patient findByUniqueId(String uniqueId);

}
