package org.ssdlv.categoryservice.tags;

import org.ssdlv.categoryservice.categories.CategoryRepository;

public class TagFaker {

    public static void tagFaker(TagRepository tagRepository, CategoryRepository categoryRepository) {
        tagRepository.save(new Tag("Tag One", categoryRepository.getOne(1L)));
        tagRepository.save(new Tag("Tag Two", categoryRepository.getOne(2L)));
        tagRepository.save(new Tag("Tag Three", categoryRepository.getOne(3L)));
        tagRepository.save(new Tag("Tag Four", categoryRepository.getOne(4L)));
        tagRepository.save(new Tag("Tag Five", categoryRepository.getOne(5L)));
    }
}
