package org.ssdlv.jobservice.cities;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ssdlv.jobservice.jobs.Job;

@Configuration
public class CityFaker {
    private final CityRepository cityRepository;

    public CityFaker(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Bean("city_faker")
    public CommandLineRunner start() {
        return args -> {
            cityRepository.save(new City("Casablanca"));
            cityRepository.save(new City("Meknes"));
            cityRepository.save(new City("El Jadida"));
            cityRepository.save(new City("FES"));
            cityRepository.save(new City("Tanger"));
        };
    }
}
