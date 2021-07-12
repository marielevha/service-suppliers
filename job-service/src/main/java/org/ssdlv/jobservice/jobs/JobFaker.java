package org.ssdlv.jobservice.jobs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.ssdlv.jobservice.categories.CategoryService;
import org.ssdlv.jobservice.cities.CityRepository;
import org.ssdlv.jobservice.users.User;
import org.ssdlv.jobservice.users.UserService;
import org.ssdlv.jobservice.utils.Constants;

@Configuration
public class JobFaker {
    private final JobRepository jobRepository;
    private final CityRepository cityRepository;

    public JobFaker(JobRepository jobRepository, CityRepository cityRepository) {
        this.jobRepository = jobRepository;
        this.cityRepository = cityRepository;
    }

    @Bean("job_faker")
    public CommandLineRunner start() {
        return args -> {
            jobRepository.save(new Job("Job One", Constants.FAKE_DESCRIPTION, 1L, 1L, cityRepository.getOne(1L)));
            jobRepository.save(new Job("Job Two", Constants.FAKE_DESCRIPTION, 1L, 1L, cityRepository.getOne(2L)));
            jobRepository.save(new Job("Job Three", Constants.FAKE_DESCRIPTION, 1L, 2L, cityRepository.getOne(3L)));
            jobRepository.save(new Job("Job Four", Constants.FAKE_DESCRIPTION, 1L, 2L, cityRepository.getOne(1L)));
            jobRepository.save(new Job("Job Five", Constants.FAKE_DESCRIPTION, 1L, 3L, cityRepository.getOne(2L)));
        };
    }
}
