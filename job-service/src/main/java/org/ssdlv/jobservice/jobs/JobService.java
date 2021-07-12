package org.ssdlv.jobservice.jobs;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.ssdlv.jobservice.categories.Category;
import org.ssdlv.jobservice.categories.CategoryService;
import org.ssdlv.jobservice.offers.Offer;
import org.ssdlv.jobservice.tags.Tag;
import org.ssdlv.jobservice.tags.TagRepository;
import org.ssdlv.jobservice.users.User;
import org.ssdlv.jobservice.users.UserService;
import org.ssdlv.jobservice.utils.Constants;
import org.ssdlv.jobservice.utils.SlugifyUtil;
import org.ssdlv.jobservice.utils.requests.JobFilterRequest;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final TagRepository tagRepository;

    public JobService(JobRepository jobRepository, UserService userService, CategoryService categoryService, TagRepository tagRepository) {
        this.jobRepository = jobRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.tagRepository = tagRepository;
    }

    public Job create(Job job) {
        User user = userService.findUSerById(job.getUserId());
        Category category = categoryService.findCategoryById(job.getCategoryId());

        job.setUserId(user.getId());
        job.setCategoryId(category.getId());
        job.setUser(user);
        job.setCategory(category);
        job.setCreatedAt(new Date());
        job.setUpdatedAt(new Date());
        job.setSlug(SlugifyUtil.getInstance().slugify((job.getTitle())));

        return jobRepository.save(job);
    }

    public Job update(Job newJob, Long id) throws NotFound {
        Job job = jobRepository.findById(id).orElseThrow(NotFound::new);

        job.setTitle(newJob.getTitle());
        job.setDescription(newJob.getDescription());
        job.setCategoryId(newJob.getCategoryId());
        job.setUserId(newJob.getUserId());

        job.setUpdatedAt(new Date());
        job.setSlug(SlugifyUtil.getInstance().slugify((job.getTitle())));
        return jobRepository.save(job);
    }

    public Job publish(Long id) throws NotFound {
        Job job = jobRepository.findById(id).orElseThrow(NotFound::new);
        job.setPublishedAt(LocalDate.now());
        return jobRepository.save(job);
    }

    public Job unPublish(Long id) throws NotFound {
        Job job = jobRepository.findById(id).orElseThrow(NotFound::new);
        job.setPublishedAt(null);
        return jobRepository.save(job);
    }

    public Boolean delete(Long id) throws NotFound {
        Job tag = jobRepository.findById(id).orElseThrow(NotFound::new);
        tag.setDeletedAt(new Date());
        return jobRepository.save(tag).getDeletedAt() != null;
    }

    public Job findById(Long id) throws NotFound {
        Job job = jobRepository.findById(id).orElseThrow(NotFound::new);

        job.setUser(userService.findUSerById(job.getUserId()));
        job.setCategory(categoryService.findCategoryById(job.getCategoryId()));
        return  job;
    }

    public Page<Job> all(Pageable pageable, String state, Long userId, Long categoryId) {
        Page<Job> jobs;
        if (state.equals(Constants.PUBLISH_KEY)) {
            jobs = jobRepository.findAllByPublishedAtIsNotNull(pageable);
        }
        else if (state.equals(Constants.PUBLISH_NOT_KEY)) {
           jobs = jobRepository.findAllByPublishedAtIsNull(pageable);
        }
        else {
            jobs = jobRepository.findAll(pageable);
        }

        jobs.forEach(job -> {
            job.setUser(userService.findUSerById(job.getUserId()));
            job.setCategory(categoryService.findCategoryById(job.getCategoryId()));
        });
        return jobs;
    }

    /*public Page<Job>data(String state, Long userId, Long categoryId, Pageable pageable) {
        List<Job> jobs = jobRepository.findAll();

        if (state.equals(Constants.PUBLISH_KEY)) {
            jobs = jobs
                    .stream()
                    .filter(job -> job.getPublishedAt() != null)
                    .collect(Collectors.toList());
        }
        if (state.equals(Constants.PUBLISH_NOT_KEY)) {
            jobs = jobs
                    .stream()
                    .filter(job -> job.getPublishedAt() == null)
                    .collect(Collectors.toList());
        }
        if (state.equals(Constants.DELETED_KEY)) {
            jobs = jobs
                    .stream()
                    .filter(job -> job.getDeletedAt() != null)
                    .collect(Collectors.toList());
        }

        if (categoryId != 0) {
            jobs = jobs
                    .stream()
                    .filter(job -> job.getCategoryId().equals(categoryId))
                    .collect(Collectors.toList());
        }
        if (userId != 0) {
            jobs = jobs
                    .stream()
                    .filter(job -> job.getUserId().equals(userId))
                    .collect(Collectors.toList());
        }

        if (userId != 0) {
            jobs = jobs
                    .stream()
                    .filter(job -> job.getUserId().equals(userId))
                    .collect(Collectors.toList());
        }

        jobs.forEach(job -> {
            job.setUser(userService.findUSerById(job.getUserId()));
            job.setCategory(categoryService.findCategoryById(job.getCategoryId()));
        });

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), jobs.size());
        return new PageImpl<>(jobs.subList(start, end), pageable, jobs.size());
    }*/

    public Page<Job>data(JobFilterRequest filter, Pageable pageable) {
        List<Job> jobs;
        System.err.println("FILTER " + filter.toString());

        if (filter.getStart() != null && filter.getEnd() != null) {
            jobs = jobRepository.findAllByPublishedAtBetween(filter.getStart(), filter.getEnd());
        }
        else jobs = jobRepository.findAll();

        System.err.println(jobs.size());

        jobs.forEach(job -> {
            System.err.println("JOB " + job.toString());
        });

        if (filter.isPublished()) {
            jobs = jobs
                    .stream()
                    .filter(job -> (job.getPublishedAt() != null && job.getDeletedAt() == null))
                    .collect(Collectors.toList());
        }
        if (filter.isDeleted()) {
            jobs = jobs
                    .stream()
                    .filter(job -> job.getDeletedAt() != null)
                    .collect(Collectors.toList());
        }
        if (filter.getCategoryId() != null) {
            jobs = jobs
                    .stream()
                    .filter(job -> job.getCategoryId().equals(filter.getCategoryId()))
                    .collect(Collectors.toList());
        }
        if (filter.getCategories() != null && !filter.getCategories().isEmpty()) {
            jobs = jobs
                    .stream()
                    .filter(job -> filter.getCategories().contains(job.getCategoryId()))
                    .collect(Collectors.toList());
        }
        if (filter.getCities() != null && !filter.getCities().isEmpty()) {
            System.err.println("cities null");
            jobs = jobs
                    .stream()
                    .filter(job -> filter.getCities().contains(job.getCity().getId()))
                    .collect(Collectors.toList());
        }
        if (filter.getUserId() != null) {
            jobs = jobs
                    .stream()
                    .filter(job -> job.getUserId().equals(filter.getUserId()))
                    .collect(Collectors.toList());
        }

        /*jobs = jobs
                .stream()
                .sorted(Comparator.comparing(Job::getCreatedAt).reversed())
                .collect(Collectors.toList());*/

        jobs.forEach(job -> {
            //System.err.println(userService.findUSerById(job.getUserId()));
            //System.err.println(categoryService.findCategoryById(job.getCategoryId()));
            job.setUser(userService.findUSerById(job.getUserId()));
            job.setCategory(categoryService.findCategoryById(job.getCategoryId()));
        });

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), jobs.size());
        return new PageImpl<>(jobs.subList(start, end), pageable, jobs.size());
    }

    public int count_jobs_by_category(Long id) {
        return jobRepository
                .findAllByCategoryIdAndDeletedAtIsNullAndPublishedAtIsNotNull(id)
                .size();
    }

    public List<Job> find_and_filter_data(JobFilterRequest filter) {
        System.err.println(filter.toString());
        List<Job> jobs;
        if (filter.getStart() != null && filter.getEnd() != null) {
            jobs = jobRepository.findAllByPublishedAtBetween(filter.getStart(), filter.getEnd());
        }
        else jobs = jobRepository.findAll();

        jobs.forEach(job -> {
            job.setTotalOffers(job.getOffers().size());
            job.setTagsSlug(generateTagSlug(job.getTags()));
            /*job.getTags().forEach(tag -> {
                job.getTagsSlug().add(SlugifyUtil.getInstance().slugify(tag.getText()));
            });*/
        });

        //Filter jobs contains {string}
        if (filter.getQuery() != null && !filter.getQuery().isEmpty()) {
            jobs = jobs
                    .stream()
                    .filter(job -> (
                        job.getTitle().toLowerCase().contains(filter.getQuery().toLowerCase())) ||
                        job.getDescription().toLowerCase().contains(filter.getQuery().toLowerCase())
                    )
                    .collect(Collectors.toList());
        }
        //Filter jobs contains {tag}
        if (filter.getTags() != null && !filter.getTags().isEmpty()) {
            jobs = jobs
                    .stream()
                    .filter(job -> checkTag(job.getTagsSlug(), filter.getTags()))
                    .collect(Collectors.toList());
        }

        if (filter.isPublished()) {
            jobs = jobs
                    .stream()
                    .filter(job -> (job.getPublishedAt() != null && job.getDeletedAt() == null))
                    .collect(Collectors.toList());
        }
        if (filter.isDeleted()) {
            jobs = jobs
                    .stream()
                    .filter(job -> job.getDeletedAt() != null)
                    .collect(Collectors.toList());
        }
        if (filter.getCategoryId() != null) {
            jobs = jobs
                    .stream()
                    .filter(job -> job.getCategoryId().equals(filter.getCategoryId()))
                    .collect(Collectors.toList());
        }
        if (filter.getCategories() != null && !filter.getCategories().isEmpty()) {
            jobs = jobs
                    .stream()
                    .filter(job -> filter.getCategories().contains(job.getCategoryId()))
                    .collect(Collectors.toList());
        }
        if (filter.getCities() != null && !filter.getCities().isEmpty()) {
            System.err.println("cities null");
            jobs = jobs
                    .stream()
                    .filter(job -> filter.getCities().contains(job.getCity().getId()))
                    .collect(Collectors.toList());
        }
        if (filter.getUserId() != null) {
            jobs = jobs
                    .stream()
                    .filter(job -> job.getUserId().equals(filter.getUserId()))
                    .collect(Collectors.toList());
        }

        if (filter.getOrder() != null && filter.getOrder().equals("more-offers")) {
            jobs = jobs
                    .stream()
                    .sorted(Comparator.comparing(Job::getTotalOffers).reversed())
                    .collect(Collectors.toList());
        }
        else if (filter.getOrder() != null && filter.getOrder().equals("less-offers")) {
            jobs = jobs
                    .stream()
                    .sorted(Comparator.comparing(Job::getTotalOffers))
                    .collect(Collectors.toList());
        }
        else /*if (filter.getOrder() != null && filter.getOrder().equals("publishedAt"))*/ {
            jobs = jobs
                    .stream()
                    .sorted(Comparator.comparing(Job::getPublishedAt).reversed())
                    .collect(Collectors.toList());
        }

        jobs.forEach(job -> {
            job.setUser(userService.findUSerById(job.getUserId()));
            job.setCategory(categoryService.findCategoryById(job.getCategoryId()));
        });

        return jobs;
    }

    public Page<Job> paginate_job(List<Job> jobs, Pageable pageable) {
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), jobs.size());
        return new PageImpl<>(jobs.subList(start, end), pageable, jobs.size());
    }

    public int count_job(String status) {
        List<Job> jobs = jobRepository.findAll();

        if (status.equals("published")) {
            jobs = jobs
                    .stream()
                    .filter(job -> (job.getPublishedAt() != null && job.getDeletedAt() == null))
                    .collect(Collectors.toList());
        }
        if (status.equals("not-published")) {
            jobs = jobs
                    .stream()
                    .filter(job -> (job.getPublishedAt() != null && job.getDeletedAt() == null))
                    .collect(Collectors.toList());
        }
        if (status.equals("deleted")) {
            jobs = jobs
                    .stream()
                    .filter(job -> job.getDeletedAt() != null)
                    .collect(Collectors.toList());
        }

        return jobs.size();
    }

    public Map<LocalDate, Integer> job_daily_published(LocalDate start, LocalDate end, boolean deleted) {
        List<Job> jobs = jobRepository.findAllByPublishedAtBetween(start, end);

        if (!deleted) {
            jobs = jobs
                    .stream()
                    .filter(job -> job.getDeletedAt() == null)
                    .collect(Collectors.toList());
        }

        Map<LocalDate, List<Job>> groupJobs = jobs
                .stream()
                .collect(Collectors.groupingBy(Job::getPublishedAt));

        Map<LocalDate, Integer> map = new HashMap<>();

        groupJobs.values().forEach(jobs1 -> {
            map.put(jobs1.get(0).getPublishedAt(), jobs1.size());
        });

        return map;
    }

    public Collection<Offer> offers(Long id, String status) throws NotFound {
        Job job = jobRepository.findById(id).orElseThrow(NotFound::new);

        Collection<Offer> offers = job.getOffers();

        if (status.equals("activated")) {
            offers = offers
                    .stream()
                    .filter(offer -> (offer.getActivatedAt() != null && offer.getDeletedAt() == null))
                    .collect(Collectors.toList());
        }
        else if (status.equals("not-deleted")) {
            offers = offers
                    .stream()
                    .filter(offer -> (offer.getDeletedAt() == null))
                    .collect(Collectors.toList());
        }


        return offers;
    }

    private boolean checkTag(Collection<String> jobTag, Collection<String> filterTag) {
        System.err.println(jobTag);
        for (String value : jobTag) {
            System.err.println("JOB TAG : " + value);
            for (String s : filterTag) {
                System.err.println("FILTER TAG : " + s);
                if (value.equals(SlugifyUtil.getInstance().slugify(s))) {
                    System.err.println("MATCH " + value + ":" + s);
                    return true;
                }
                //return value.equals(SlugifyUtil.getInstance().slugify(s));
            }
        }
        return false;
    }

    private Collection<String> generateTagSlug(Collection<Tag> tags) {
        Collection<String> tagsSlug = new ArrayList<>();
        tags.forEach(tag -> {
            tagsSlug.add(SlugifyUtil.getInstance().slugify(tag.getText()));
        });
        return tagsSlug;
    }

}