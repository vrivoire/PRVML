package com.vrivoire.prvml.service;

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

    /**
     *
     * @return
     */
    @Bean
    @Primary
    public BookingService bookingService() {
        return Mockito.mock(BookingService.class);
    }
}
