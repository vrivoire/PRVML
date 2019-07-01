package com.vrivoire.prvml.repositories;

import com.vrivoire.prvml.model.Professional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Vincent
 */
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

    /**
     *
     * @param uniqueId
     * @return
     */
    Professional findByUniqueId(String uniqueId);
}
