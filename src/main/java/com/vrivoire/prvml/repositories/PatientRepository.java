package com.vrivoire.prvml.repositories;

import com.vrivoire.prvml.model.Patient;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Vincent
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     *
     * @param uniqueId
     * @return
     */
    Patient findByUniqueId(String uniqueId);
}
