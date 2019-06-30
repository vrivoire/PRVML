package com.vrivoire.prvml.service;

import com.vrivoire.prvml.model.Professional;

import java.util.List;

public interface ProfessionalService {

    List<Professional> loadAll();

    Professional getProfessional(Long id);

    Professional findByUniqueId(String uniqueId);

    public Professional getProfessionalForAppointment(Long appointementId);
}
