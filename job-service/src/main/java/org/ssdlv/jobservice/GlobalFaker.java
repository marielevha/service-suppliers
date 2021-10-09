package org.ssdlv.jobservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ssdlv.jobservice.cities.City;
import org.ssdlv.jobservice.cities.CityFaker;
import org.ssdlv.jobservice.cities.CityRepository;
import org.ssdlv.jobservice.jobs.Job;
import org.ssdlv.jobservice.jobs.JobFaker;
import org.ssdlv.jobservice.jobs.JobRepository;
import org.ssdlv.jobservice.offers.Offer;
import org.ssdlv.jobservice.offers.OfferFaker;
import org.ssdlv.jobservice.offers.OfferRepository;
import org.ssdlv.jobservice.tags.Tag;
import org.ssdlv.jobservice.tags.TagFaker;
import org.ssdlv.jobservice.tags.TagRepository;
import org.ssdlv.jobservice.utils.Constants;

@Configuration
public class GlobalFaker {
    private final CityRepository cityRepository;
    private final JobRepository jobRepository;
    private final OfferRepository offerRepository;
    private final TagRepository tagRepository;

    public GlobalFaker(CityRepository cityRepository, JobRepository jobRepository, OfferRepository offerRepository, TagRepository tagRepository) {
        this.cityRepository = cityRepository;
        this.jobRepository = jobRepository;
        this.offerRepository = offerRepository;
        this.tagRepository = tagRepository;
    }

    @Bean("global_faker")
    public CommandLineRunner start() {
        return args -> {
            //CITIES
            CityFaker.cityFaker(cityRepository);

            //JOBS
            JobFaker.jobFaker(jobRepository, cityRepository);

            //OFFERS
            OfferFaker.offerFaker(offerRepository, jobRepository);

            //TAGS
            TagFaker.tagFaker(tagRepository, jobRepository);

        };
    }

}
