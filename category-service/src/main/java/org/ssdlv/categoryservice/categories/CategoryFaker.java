package org.ssdlv.categoryservice.categories;

public class CategoryFaker {

    public static void categoryFaker(CategoryRepository categoryRepository) {
        categoryRepository.save(new Category("Category Title One", "Category Description One", true));
        categoryRepository.save(new Category("Category Title Two", "Category Description Two", true));
        categoryRepository.save(new Category("Category Title Three", "Category Description Three", true));
        categoryRepository.save(new Category("Category Title Four", "Category Description Four", true));
        categoryRepository.save(new Category("Category Title Five", "Category Description Five", true));
    }
}
