package com.vrivoire.prvml.repositories;

import com.vrivoire.prvml.model.Patient;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByUniqueId(String uniqueId);
}
