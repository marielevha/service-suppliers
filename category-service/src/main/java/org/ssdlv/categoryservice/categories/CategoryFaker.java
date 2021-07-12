package org.ssdlv.categoryservice.categories;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class CategoryFaker {
    private CategoryRepository categoryRepository;

    public CategoryFaker(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Bean("category_faker")
    public CommandLineRunner start() {
        return args -> {
            categoryRepository.save(new Category(
                "Category Title One",
                "Category Description One",
                true
            ));

            categoryRepository.save(new Category(
                "Category Title Two",
                "Category Description Two",
                true
            ));

            categoryRepository.save(new Category(
                "Category Title Three",
                "Category Description Three",
                true
            ));

            categoryRepository.save(new Category(
                "Category Title Four",
                "Category Description Four",
                true
            ));

            categoryRepository.save(new Category(
                "Category Title Five",
                "Category Description Five",
                true
            ));
        };
    }
}
