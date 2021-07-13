package org.ssdlv.jobservice.offers;

import org.ssdlv.jobservice.jobs.JobRepository;

public class OfferFaker {

    public static void offerFaker(OfferRepository offerRepository, JobRepository jobRepository) {
        offerRepository.save(new Offer("Offer One", "Description Offer One", 100, jobRepository.getOne(1L)));
        offerRepository.save(new Offer("Offer Two", "Description Offer Two", 125, jobRepository.getOne(1L)));
        offerRepository.save(new Offer("Offer Three", "Description Offer Three", 110, jobRepository.getOne(2L)));
        offerRepository.save(new Offer("Offer Four", "Description Offer Four", 95, jobRepository.getOne(2L)));
        offerRepository.save(new Offer("Offer Five", "Description Offer Five", 225, jobRepository.getOne(3L)));
    }
}
