package org.ssdlv.categoryservice.tags;

import org.omg.CosNaming.NamingContextPackage.NotFound;
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
import org.ssdlv.categoryservice.utils.requests.AddTagRequest;
import org.ssdlv.categoryservice.utils.responses.ErrorResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Transactional
@CrossOrigin("*")
//@RequestMapping("/api/v1/")
@RestController
public class TagController {
    private final TagService tagService;
    private final TagRepository tagRepository;

    public TagController(TagService tagService, TagRepository tagRepository) {
        this.tagService = tagService;
        this.tagRepository = tagRepository;
    }

    @GetMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('TAG:READ')")
    public Page<Tag> all(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "12") int size,
            @RequestParam(name = "column", defaultValue = "createdAt") String column
    ) {
        Sort sort = Sort.by(column).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return tagRepository.findAll(pageable);
    }

    @GetMapping(value = "/tags/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('TAG:READ')")
    public ResponseEntity<?> find(
            @PathVariable(value = "id") Long id,
            HttpServletRequest request
    ) {
        try {
            Tag tag = tagService.find(id);
            return ResponseEntity.status(HttpStatus.OK).body(tag);
        }
        catch (NotFound found) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "Tag {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/tags")
    @PreAuthorize("hasAnyAuthority('TAG:CREATE')")
    public ResponseEntity<Tag> create(@Valid @RequestBody AddTagRequest tagRequest) {
        try {
            Tag tag = tagService.create(tagRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(tag);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/tags/{id}")
    @PreAuthorize("hasAnyAuthority('TAG:UPDATE')")
    public ResponseEntity<?> update(
            @Valid @RequestBody Tag tag,
            @PathVariable(name = "id") Long id,
            HttpServletRequest request
    ) {
        try {
            tag = tagService.update(tag, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(tag);
        }
        catch (NotFound found) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "Tag {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/tags/{id}")
    @PreAuthorize("hasAnyAuthority('TAG:DELETE')")
    public ResponseEntity<?> delete(
            @PathVariable(value = "id") Long id,
            HttpServletRequest request
    ) {
        try {
            Boolean deleted = tagService.delete(id);
            if (deleted) {
                return ResponseEntity.status(HttpStatus.OK).body("Tag Deleted");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (NotFound found) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "Tag {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
