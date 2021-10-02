package org.ssdlv.categoryservice.categories;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.ssdlv.categoryservice.utils.responses.ErrorResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Transactional
@RestController
public class CategoryController {
    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('CATEGORY:READ')")
    public ResponseEntity<Page<Category>> all(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "15") int size,
        @RequestParam(name = "column", defaultValue = "createdAt") String column,
        @RequestParam(name = "status", defaultValue = "activated") String status
    ) {
        try {
            Sort sort = Sort.by(column).descending();
            Pageable pageable = PageRequest.of(page, size, sort);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(categoryService.data(pageable, status));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        //return categoryRepository.findAll(pageable);
    }

    @GetMapping("/categories/{id}")
    @PreAuthorize("hasAnyAuthority('CATEGORY:READ')")
    public ResponseEntity<?> find(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request
    ) {
        try {
            Category category = categoryService.find(id);
            return ResponseEntity.status(HttpStatus.OK).body(category);
        }
        catch (NotFound found) {
            logger.debug("Category: {} is not found.", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "Category {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('CATEGORY:CREATE')")
    public ResponseEntity<Category> create(@Valid @RequestBody Category category) {
        try {
            category = categoryService.create(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(category);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            //System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/categories/{id}")
    @PreAuthorize("hasAnyAuthority('CATEGORY:UPDATE')")
    public ResponseEntity<?> update(
        @Valid @RequestBody Category category,
        @PathVariable(name = "id") Long id,
        HttpServletRequest request
    ) {
        try {
            category = categoryService.update(category, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(category);
        }
        catch (NotFound found) {
            logger.debug("Category: {} is not found.", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                            "Category {"+id+"} is not found !",
                            request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            //System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/categories/{id}")
    @PreAuthorize("hasAnyAuthority('CATEGORY:DELETE')")
    public ResponseEntity<?> delete(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request
    ) {
        try {
            Boolean deleted = categoryService.delete(id);
            if (deleted) {
                return ResponseEntity.status(HttpStatus.OK).body("Category Is Deleted");
            }
            logger.debug("Category: {} is not found.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (NotFound found) {
            logger.debug("Category: {} is found.", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "Category {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
