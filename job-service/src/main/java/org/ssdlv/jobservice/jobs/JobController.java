package org.ssdlv.jobservice.jobs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(JobController.class);
    private final JobService jobService;
    private final TagService tagService;

    public JobController(JobService jobService, TagService tagService) {
        this.jobService = jobService;
        this.tagService = tagService;
    }

    @Operation(summary = "Méthode permettant de récupérer un job par <<ID>>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(schema = @Schema(implementation = Job.class)) }),
    })
    @GetMapping("/jobs/{id}")
    @PreAuthorize("hasAnyAuthority('JOB:READ')")
    public ResponseEntity<?> find(
        @PathVariable(name = "id") Long id,
        HttpServletRequest request
    ) {
        try {
            Job job = jobService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(job);
        }
        catch (NotFound found) {
            logger.debug("Job: {} is not found.", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "Job {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Méthode permettant de récupérer un job par <<SLUG>>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(schema = @Schema(implementation = Job.class)) }),
    })
    @GetMapping("/jobs/{slug}/one")
    @PreAuthorize("hasAnyAuthority('JOB:READ')")
    public ResponseEntity<?> find(
        @PathVariable(name = "slug") String slug,
        HttpServletRequest request
    ) {
        try {
            Job job = jobService.findBySlug(slug);
            return ResponseEntity.status(HttpStatus.OK).body(job);
        }
        catch (NotFound found) {
            logger.debug("Job: {} is not found.", slug);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                            "Job {"+slug+"} is not found !",
                            request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Méthode permettant de créer et publier un job")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(schema = @Schema(implementation = Job.class)) }),
    })
    @PostMapping("/jobs")
    @PreAuthorize("hasAnyAuthority('JOB:CREATE')")
    public ResponseEntity<Job> create(@Valid @RequestBody CreateJobRequest request) {
        try {
            Job job = jobService.create(request.getJob());
            job = jobService.publish(job.getId());
            tagService.createMultiple(request.getTags(), job);
            return ResponseEntity.status(HttpStatus.CREATED).body(job);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Méthode permettant de mettre à jour un job par <<ID>>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(schema = @Schema(implementation = Job.class)) }),
    })
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
            logger.debug("Job: {} is not found.", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "Job {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Méthode permettant de publier un job par <<ID>>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(schema = @Schema(implementation = Job.class)) }),
    })
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
            logger.debug("Job: {} is not found.", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "Job {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Méthode permettant de rétirer un job publié par <<ID>>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = { @Content(schema = @Schema(implementation = Job.class)) }),
    })
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
            logger.debug("Job: {} is not found.", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "Job {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Méthode permettant de supprimer un job par <<ID>>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content),
    })
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
            logger.debug("Job: {} is not found.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (NotFound found) {
            logger.debug("Job: {} is not found.", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "Job {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Méthode permettant de récupérer la liste des offres par job")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content),
    })
    @GetMapping("/jobs/{id}/offers")
    @PreAuthorize("hasAnyAuthority('JOB:READ')")
    public ResponseEntity<?> offers_by_job(
        @PathVariable(name = "id") Long id,
        @RequestParam(name = "user", required = false) Long userId,
        @RequestParam(name = "status", defaultValue = "not-deleted") String status,
        HttpServletRequest request
    ) {

        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jobService.offers(id, userId, status));
        }
        catch (NotFound notFound) {
            logger.debug("Job: {} is not found.", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "Job {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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

    @Operation(summary = "Méthode permettant de récupérer liste de jobs avec pagination avec possiblité de filtrer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content),
    })
    @PostMapping("/jobs_data")
    @PreAuthorize("hasAnyAuthority('JOB:READ')")
    public ResponseEntity<?> data(@RequestBody JobFilterRequest filter) {
        try {
            Pageable pageable;
            if (filter.getSize() != 0) {
                pageable = PageRequest.of(filter.getPage(), filter.getSize());
            } else {
                pageable = PageRequest.of(filter.getPage(), 10);
            }
            List<Job> jobs = jobService.find_and_filter_data(filter);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jobService.paginate_job(jobs, pageable));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Operation(summary = "Méthode permettant de compter les jobs par <<CATEGORY>>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content),
    })
    @GetMapping("/jobs/{category}/count")
    @PreAuthorize("hasAnyAuthority('JOB:READ')")
    public ResponseEntity<?> count_jobs_by_category(@PathVariable(name = "category") Long category) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jobService.count_jobs_by_category(category));
        }
        catch(Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
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
