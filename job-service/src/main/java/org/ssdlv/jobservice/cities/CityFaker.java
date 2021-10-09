package org.ssdlv.jobservice.cities;

public class CityFaker {

    public static void cityFaker(CityRepository cityRepository) {
        cityRepository.save(new City("Casablanca"));
        cityRepository.save(new City("Meknes"));
        cityRepository.save(new City("El Jadida"));
        cityRepository.save(new City("FES"));
        cityRepository.save(new City("Tanger"));
    }
}
