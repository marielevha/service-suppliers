package org.ssdlv.jobservice.tags;

import org.ssdlv.jobservice.jobs.JobRepository;

public class TagFaker {

    public static void tagFaker(TagRepository tagRepository, JobRepository jobRepository) {
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
    }
}
