package org.ssdlv.categoryservice.categories;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryFaker {
    private CategoryRepository categoryRepository;

    public CategoryFaker(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //@Bean
    public CommandLineRunner start() {
        return args -> {
            categoryRepository.save(new Category(
                "Category Title One",
                "Category Description One",
                "category-title-one",
                true
            ));

            categoryRepository.save(new Category(
                "Category Title Two",
                "Category Description Two",
                "category-title-two",
                true
            ));

            categoryRepository.save(new Category(
                "Category Title Three",
                "Category Description Three",
                "category-title-three",
                true
            ));

            categoryRepository.save(new Category(
                "Category Title Four",
                "Category Description Four",
                "category-title-four",
                true
            ));

            categoryRepository.save(new Category(
                "Category Title Five",
                "Category Description Five",
                "category-title-five",
                true
            ));
        };
    }
}
