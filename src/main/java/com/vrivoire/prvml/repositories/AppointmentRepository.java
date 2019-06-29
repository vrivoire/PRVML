package com.vrivoire.prvml.repositories;

import com.vrivoire.prvml.model.Appointment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
