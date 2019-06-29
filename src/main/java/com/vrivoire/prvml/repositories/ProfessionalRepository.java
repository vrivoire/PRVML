package com.vrivoire.prvml.repositories;

import com.vrivoire.prvml.model.Professional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

    Professional findByUniqueId(String uniqueId);
}
