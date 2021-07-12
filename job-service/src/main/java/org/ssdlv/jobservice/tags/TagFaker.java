package org.ssdlv.jobservice.tags;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ssdlv.jobservice.cities.City;
import org.ssdlv.jobservice.jobs.JobRepository;

@Configuration
public class TagFaker {
    private final TagRepository tagRepository;
    private final JobRepository jobRepository;

    public TagFaker(TagRepository tagRepository, JobRepository jobRepository) {
        this.tagRepository = tagRepository;
        this.jobRepository = jobRepository;
    }

    @Bean("tag_faker")
    public CommandLineRunner start() {
        return args -> {
            tagRepository.save(new Tag("Bricolage", jobRepository.getOne(1L)));
            tagRepository.save(new Tag("House", jobRepository.getOne(1L)));
            tagRepository.save(new Tag("Baby Siting", jobRepository.getOne(1L)));
            tagRepository.save(new Tag("Reparation", jobRepository.getOne(1L)));
            tagRepository.save(new Tag("Gardener", jobRepository.getOne(1L)));

            tagRepository.save(new Tag("Bricolage", jobRepository.getOne(2L)));
            tagRepository.save(new Tag("Medical", jobRepository.getOne(2L)));
            tagRepository.save(new Tag("Electronic", jobRepository.getOne(2L)));
            tagRepository.save(new Tag("Reparation", jobRepository.getOne(2L)));
            tagRepository.save(new Tag("Plumbing", jobRepository.getOne(2L)));

            tagRepository.save(new Tag("Bricolage", jobRepository.getOne(3L)));
            tagRepository.save(new Tag("Delivery", jobRepository.getOne(3L)));
            tagRepository.save(new Tag("Baby Siting", jobRepository.getOne(3L)));
            tagRepository.save(new Tag("Renovation", jobRepository.getOne(3L)));
            tagRepository.save(new Tag("Help", jobRepository.getOne(3L)));

            tagRepository.save(new Tag("Sewing", jobRepository.getOne(4L)));
            tagRepository.save(new Tag("House", jobRepository.getOne(4L)));
            tagRepository.save(new Tag("Animals", jobRepository.getOne(4L)));
            tagRepository.save(new Tag("Services", jobRepository.getOne(4L)));
            tagRepository.save(new Tag("Private Lessons", jobRepository.getOne(4L)));

            tagRepository.save(new Tag("Informatics", jobRepository.getOne(5L)));
            tagRepository.save(new Tag("Decoration", jobRepository.getOne(5L)));
            tagRepository.save(new Tag("Animals", jobRepository.getOne(5L)));
            tagRepository.save(new Tag("Troubleshooting", jobRepository.getOne(5L)));
        };
    }
}
