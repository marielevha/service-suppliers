package org.ssdlv.jobservice.jobs;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.ssdlv.jobservice.offers.OfferRepository;
import org.ssdlv.jobservice.tags.TagService;
import org.ssdlv.jobservice.utils.Constants;
import org.ssdlv.jobservice.utils.requests.CreateJobRequest;
import org.ssdlv.jobservice.utils.requests.JobFilterRequest;
import org.ssdlv.jobservice.utils.responses.ErrorResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Transactional
@CrossOrigin("*")
@RestController
public class JobController {
    private final JobService jobService;
    private final TagService tagService;

    public JobController(JobService jobService, TagService tagService) {
        this.jobService = jobService;
        this.tagService = tagService;
    }

    @GetMapping("/jobs/{id}")
    @PreAuthorize("hasAnyAuthority('JOB:READ')")
    public ResponseEntity<?> find(
            @PathVariable(name = "id") Long id,
            HttpServletRequest request
    ) {
        /*Job job = jobService.findById(id);
        EntityModel<Job> resource = EntityModel.of(job);

        Link selfLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(JobController.class).find(id))
                .withSelfRel();
        resource.add(selfLink);
        return new ResponseEntity<>(resource, HttpStatus.OK);*/
        try {
            Job job = jobService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(job);
        }
        catch (NotFound found) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "Job {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/jobs")
    @PreAuthorize("hasAnyAuthority('JOB:CREATE')")
    public ResponseEntity<Job> create(@Valid @RequestBody CreateJobRequest request) {
        try {
            Job job = jobService.create(request.getJob());
            tagService.createMultiple(request.getTags(), job);
            return ResponseEntity.status(HttpStatus.CREATED).body(job);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/jobs/{id}")
    @PreAuthorize("hasAnyAuthority('JOB:UPDATE')")
    public ResponseEntity<?> update(
            @Valid @RequestBody Job job,
            @PathVariable(name = "id") Long id,
            HttpServletRequest request
    ) {
        try {
            job = jobService.update(job, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(job);
        }
        catch (NotFound found) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "Job {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/jobs/{id}/publish")
    @PreAuthorize("hasAnyAuthority('JOB:CREATE')")
    public ResponseEntity<?> publish(
            @PathVariable(name = "id") Long id,
            HttpServletRequest request
    ) {
        try {
            Job job = jobService.publish(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(job);
        }
        catch (NotFound found) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "Job {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/jobs/{id}/unpublish")
    @PreAuthorize("hasAnyAuthority('JOB:CREATE')")
    public ResponseEntity<?> unPublish(
            @PathVariable(name = "id") Long id,
            HttpServletRequest request
    ) {
        try {
            Job job = jobService.unPublish(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(job);
        }
        catch (NotFound found) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                            "Job {"+id+"} is not found !",
                            request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/jobs/{id}")
    @PreAuthorize("hasAnyAuthority('JOB:DELETE')")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id,
            HttpServletRequest request
    ) {
        try {
            Boolean deleted = jobService.delete(id);
            if (deleted) {
                return ResponseEntity.status(HttpStatus.OK).body("Job Is Deleted");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (NotFound found) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                            "Job {"+id+"} is not found !",
                            request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/jobs/{id}/offers")
    public ResponseEntity<?> offers_by_job(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "status", defaultValue = "not-deleted") String status,
            HttpServletRequest request
    ) {

        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jobService.offers(id, status));
        }
        catch (NotFound notFound) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "Job {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        //Job job = jobRepository.findById(id).get();

        //return ResponseEntity
        //        .status(HttpStatus.OK)
        //        .body(job.getOffers());
    }
    /*@GetMapping("/jobs/{id}/offers")
    @PreAuthorize("hasAnyAuthority('JOB:CREATE')")
    public ResponseEntity<?> jobOffer(
        @PathVariable(name = "id") Long id,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "column", defaultValue = "createdAt") String column
    ) {
        try {
            Sort sort = Sort.by(column).descending();
            Pageable pageable = PageRequest.of(page, size, sort);
            Job job = jobRepository.findById(id).get();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(offerRepository.findAllByDeletedAtIsNullAndJobMatches(job));
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/


    /*@GetMapping("/jobs")
    @PreAuthorize("hasAnyAuthority('JOB:READ')")
    public ResponseEntity<?> test(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "12") int size,
            @RequestParam(name = "state", defaultValue = "all") String state,
            @RequestParam(name = "column", defaultValue = "createdAt") String column,
            @RequestParam(name = "user", defaultValue = "0") Long userId,
            @RequestParam(name = "category", defaultValue = "0") Long categoryId
    ) {
        Sort sort = Sort.by(column).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.data(state, userId, categoryId, pageable));

    }*/

    @PostMapping("/jobs_data")
    public ResponseEntity<?> data(@RequestBody JobFilterRequest filter) {

        /*String column = Constants.DEFAULT_ORDER_COLUMN;
        if (filter.getColumn() != null) column = filter.getColumn();

        Sort sort = Sort.by(column).descending();
        if (filter.isAscending()) {
            sort = Sort.by(filter.getColumn()).ascending();
        }*/

        Pageable pageable;
        if (filter.getSize() != 0) {
           pageable  = PageRequest.of(filter.getPage(), filter.getSize());
        }
        else {
            pageable  = PageRequest.of(filter.getPage(), 10);
        }

        /*System.err.println(filter.toString());
        System.err.println(pageable.getPageSize());
        System.err.println(pageable.getPageNumber());*/

        List<Job> jobs = jobService.find_and_filter_data(filter);
        //jobService.paginate(jobs, pageable);
        //jobService.data(jobs, filter, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.paginate_job(jobs, pageable)/*jobService.data(filter, pageable)*/);
                //.body(jobService.data(filter, pageable));

    }

    @GetMapping("/jobs/{category}/count")
    public ResponseEntity<?> count_jobs_by_category(@PathVariable(name = "category") Long category) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.count_jobs_by_category(category));
    }
    /*@GetMapping("/jobs")
    @PreAuthorize("hasAnyAuthority('JOB:READ')")
    public ResponseEntity<Page<Job>> all(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "12") int size,
        @RequestParam(name = "state", defaultValue = "all") String state,
        @RequestParam(name = "column", defaultValue = "createdAt") String column,
        @RequestParam(name = "user", defaultValue = "0") Long userId,
        @RequestParam(name = "category", defaultValue = "0") Long categoryId
    ) {
        try {
            Sort sort = Sort.by(column).descending();
            Pageable pageable = PageRequest.of(page, size, sort);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jobService.all(pageable, state, userId, categoryId));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/

    @Value("${file.upload-directoy}")
    String FILE_DIRECTORY;

    @PostMapping("/upload")
    public ResponseEntity<Object> upload(@RequestParam(name = "file") MultipartFile file) {
        try {
            File _file = new File(FILE_DIRECTORY + file.getOriginalFilename());
            _file.createNewFile();

            FileOutputStream outputStream = new FileOutputStream(_file);
            outputStream.write(file.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Object>("File is upload", HttpStatus.OK);
    }
}
