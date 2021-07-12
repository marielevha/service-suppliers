package org.ssdlv.jobservice.offers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.ssdlv.jobservice.jobs.JobRepository;

@Configuration
public class OfferFaker {
    private final OfferRepository offerRepository;
    private final JobRepository jobRepository;

    public OfferFaker(OfferRepository offerRepository, JobRepository jobRepository) {
        this.offerRepository = offerRepository;
        this.jobRepository = jobRepository;
    }

    @Bean("offer_faker")
    @DependsOn({"job_faker"})
    public CommandLineRunner start() {
        return args -> {
            offerRepository.save(new Offer("Offer One", "Description Offer One", 100, jobRepository.getOne(1L)));
            offerRepository.save(new Offer("Offer Two", "Description Offer Two", 125, jobRepository.getOne(1L)));
            offerRepository.save(new Offer("Offer Three", "Description Offer Three", 110, jobRepository.getOne(2L)));
            offerRepository.save(new Offer("Offer Four", "Description Offer Four", 95, jobRepository.getOne(2L)));
            offerRepository.save(new Offer("Offer Five", "Description Offer Five", 225, jobRepository.getOne(3L)));
        };
    }
}
