package com.vrivoire.prvml.service;

import com.vrivoire.prvml.repositories.AppointmentRepository;
import com.vrivoire.prvml.repositories.AvailabilityRepository;
import com.vrivoire.prvml.repositories.PatientRepository;
import com.vrivoire.prvml.repositories.ProfessionalRepository;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 *
 * @author Vincent
 */
@Profile("test")
@Configuration
public class BookingServiceTestConfiguration {

    @Bean
    @Primary
    public PatientRepository patientRepository() {
        return Mockito.mock(PatientRepository.class);
    }

    @Bean
    @Primary
    public ProfessionalRepository professionalRepository() {
        return Mockito.mock(ProfessionalRepository.class);
    }

    @Bean
    @Primary
    public AppointmentRepository appointmentRepository() {
        return Mockito.mock(AppointmentRepository.class);
    }

    @Bean
    @Primary
    public AvailabilityRepository availabilityRepository() {
        return Mockito.mock(AvailabilityRepository.class);
    }

    @Bean
    @Primary
    public BookingService bookingService() {
        return Mockito.mock(BookingService.class);
    }
}
