package com.vrivoire.prvml.service;

import com.vrivoire.prvml.model.Professional;

import java.util.List;

/**
 *
 * @author Vincent
 */
public interface ProfessionalService {

    /**
     *
     * @return
     */
    List<Professional> loadAll();

    /**
     *
     * @param id
     * @return
     */
    Professional getProfessional(Long id);

    /**
     *
     * @param uniqueId
     * @return
     */
    Professional findByUniqueId(String uniqueId);

    /**
     *
     * @param appointementId
     * @return
     */
    public Professional getProfessionalForAppointment(Long appointementId);
}
