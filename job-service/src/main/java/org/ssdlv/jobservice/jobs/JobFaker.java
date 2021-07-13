package org.ssdlv.jobservice.jobs;

import org.ssdlv.jobservice.cities.CityRepository;
import org.ssdlv.jobservice.utils.Constants;

public class JobFaker {

    public static void jobFaker(JobRepository jobRepository, CityRepository cityRepository) {
        jobRepository.save(new Job("Job One", Constants.FAKE_DESCRIPTION, 1L, 1L, cityRepository.getOne(1L)));
        jobRepository.save(new Job("Job Two", Constants.FAKE_DESCRIPTION, 1L, 1L, cityRepository.getOne(2L)));
        jobRepository.save(new Job("Job Three", Constants.FAKE_DESCRIPTION, 1L, 2L, cityRepository.getOne(3L)));
        jobRepository.save(new Job("Job Four", Constants.FAKE_DESCRIPTION, 1L, 2L, cityRepository.getOne(1L)));
        jobRepository.save(new Job("Job Five", Constants.FAKE_DESCRIPTION, 1L, 3L, cityRepository.getOne(2L)));
    }
}
