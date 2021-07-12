package org.ssdlv.categoryservice.tags;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.ssdlv.categoryservice.categories.Category;
import org.ssdlv.categoryservice.categories.CategoryRepository;

@Configuration
public class TagFaker {
    private TagRepository tagRepository;
    private CategoryRepository categoryRepository;

    public TagFaker(TagRepository tagRepository, CategoryRepository categoryRepository) {
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
    }

    @Bean("tag_faker")
    @DependsOn({"category_faker"})
    public CommandLineRunner start() {
        return args -> {
            tagRepository.save(new Tag("Tag One", categoryRepository.getOne(1L)));
            tagRepository.save(new Tag("Tag Two", categoryRepository.getOne(2L)));
            tagRepository.save(new Tag("Tag Three", categoryRepository.getOne(3L)));
            tagRepository.save(new Tag("Tag Four", categoryRepository.getOne(4L)));
            tagRepository.save(new Tag("Tag Five", categoryRepository.getOne(5L)));
        };
    }
}
