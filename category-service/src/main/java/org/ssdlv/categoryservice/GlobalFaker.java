package org.ssdlv.categoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.ssdlv.categoryservice.categories.Category;
import org.ssdlv.categoryservice.categories.CategoryFaker;
import org.ssdlv.categoryservice.categories.CategoryRepository;
import org.ssdlv.categoryservice.tags.Tag;
import org.ssdlv.categoryservice.tags.TagFaker;
import org.ssdlv.categoryservice.tags.TagRepository;

@Configuration
public class GlobalFaker {
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;

    public GlobalFaker(TagRepository tagRepository, CategoryRepository categoryRepository) {
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
    }

    @Bean("global_faker")
    public CommandLineRunner start() {
        return args -> {
            CategoryFaker.categoryFaker(categoryRepository);

            TagFaker.tagFaker(tagRepository, categoryRepository);
        };
    }
}
