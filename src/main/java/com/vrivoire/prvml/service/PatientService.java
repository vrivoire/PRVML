package com.vrivoire.prvml.service;

import com.vrivoire.prvml.model.Patient;

import java.util.List;

/**
 *
 * @author Vincent
 */
public interface PatientService {

    /**
     *
     * @return
     */
    List<Patient> loadAll();

    /**
     *
     * @param id
     * @return
     */
    Patient getPatient(Long id);

    /**
     *
     * @param uniqueId
     * @return
     */
    Patient findByUniqueId(String uniqueId);

}
