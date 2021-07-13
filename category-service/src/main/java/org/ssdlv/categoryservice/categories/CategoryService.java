package org.ssdlv.categoryservice.categories;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.ssdlv.categoryservice.jobs.JobService;
import org.ssdlv.categoryservice.utils.SlugifyUtil;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final JobService jobService;

    public CategoryService(CategoryRepository categoryRepository, JobService jobService) {
        this.categoryRepository = categoryRepository;
        this.jobService = jobService;
    }

    public Category create(Category category) {
        category.setCreatedAt(new Date());
        category.setUpdatedAt(new Date());
        return categoryRepository.save(category);
    }

    public Category update(Category cat, Long id) throws NotFound {
        Category category = categoryRepository.findById(id).orElseThrow(NotFound::new);

        category.setTitle(cat.getTitle());
        category.setDescription(cat.getDescription());
        category.setActivated(cat.isActivated());

        category.setUpdatedAt(new Date());
        category.setSlug(SlugifyUtil.getInstance().slugify((cat.getTitle())));
        return categoryRepository.save(category);
    }

    public Boolean delete(Long id) throws NotFound {
        Category category = categoryRepository.findById(id).orElseThrow(NotFound::new);
        category.setDeletedAt(new Date());
        return categoryRepository.save(category).getDeletedAt() != null;
    }

    public Category find(Long id) throws NotFound {
        return categoryRepository.findById(id).orElseThrow(NotFound::new);
    }

    public Page<Category> data(Pageable pageable, String status) {
        List<Category> categories = categoryRepository.findAll();

        switch (status) {
            case "activated":
                categories = categories
                        .stream()
                        .filter(category -> (category.isActivated() && category.getDeletedAt() == null))
                        .collect(Collectors.toList());
                categories.forEach(category -> {
                    category.setTotalJobs(jobService.count_jobs_by_category(category.getId()));
                });
                break;
            case "not-activated":
                categories = categories
                        .stream()
                        .filter(category -> (!category.isActivated() && category.getDeletedAt() == null))
                        .collect(Collectors.toList());
                break;
            case "deleted":
                categories = categories
                        .stream()
                        .filter(category -> category.getDeletedAt() != null)
                        .collect(Collectors.toList());
                break;
        }

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), categories.size());
        return new PageImpl<>(categories.subList(start, end), pageable, categories.size());
    }

    public int count_category(String state) {
        List<Category> categories = categoryRepository.findAll();

        if (state.equals("activated")) {
            categories = categories
                    .stream()
                    .filter(category -> (category.isActivated()  && category.getDeletedAt() == null))
                    .collect(Collectors.toList());
        }
        if (state.equals("not-activated")) {
            categories = categories
                    .stream()
                    .filter(category -> (!category.isActivated()  && category.getDeletedAt() == null))
                    .collect(Collectors.toList());
        }
        if (state.equals("deleted")) {
            categories = categories
                    .stream()
                    .filter(category -> category.getDeletedAt() != null)
                    .collect(Collectors.toList());
        }

        return categories.size();
    }
}
