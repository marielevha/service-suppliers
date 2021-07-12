package org.ssdlv.jobservice.offers;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.ssdlv.jobservice.jobs.Job;
import org.ssdlv.jobservice.jobs.JobRepository;
import org.ssdlv.jobservice.utils.responses.ErrorResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Transactional
@CrossOrigin("*")
@RestController
public class OfferController {
    private final OfferService offerService;
    private final OfferRepository offerRepository;

    public OfferController(OfferService offerService, OfferRepository offerRepository) {
        this.offerService = offerService;
        this.offerRepository = offerRepository;
    }

    @GetMapping("/offers")
    @PreAuthorize("hasAnyAuthority('OFFER:READ')")
    public ResponseEntity<Page<Offer>> all(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "12") int size,
        @RequestParam(name = "column", defaultValue = "createdAt") String column
    ) {
        try {
            Sort sort = Sort.by(column).descending();
            Pageable pageable = PageRequest.of(page, size, sort);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(offerRepository.findAll(pageable));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/offers/{id}")
    @PreAuthorize("hasAnyAuthority('OFFER:READ')")
    public ResponseEntity<?> find(
            @PathVariable(name = "id") Long id,
            HttpServletRequest request
    ) {
        try {
            Offer offer = offerService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(offer);
        }
        catch (NotFound found) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                            "Offer {"+id+"} is not found !",
                            request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/offers")
    @PreAuthorize("hasAnyAuthority('OFFER:CREATE')")
    public ResponseEntity<Offer> create(@Valid @RequestBody Offer offer) {
        try {
            offer = offerService.create(offer);
            return ResponseEntity.status(HttpStatus.CREATED).body(offer);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/offers/{id}")
    @PreAuthorize("hasAnyAuthority('OFFER:UPDATE')")
    public ResponseEntity<?> update(
            @Valid @RequestBody Offer offer,
            @PathVariable(name = "id") Long id,
            HttpServletRequest request
    ) {
        try {
            offer = offerService.update(offer, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(offer);
        }
        catch (NotFound found) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                            "Offer {"+id+"} is not found !",
                            request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/offers/{id}")
    @PreAuthorize("hasAnyAuthority('OFFER:DELETE')")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id,
            HttpServletRequest request
    ) {
        try {
            Boolean deleted = offerService.delete(id);
            if (deleted) {
                return ResponseEntity.status(HttpStatus.OK).body("Offer Is Deleted");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (NotFound found) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                            "Offer {"+id+"} is not found !",
                            request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/offers/{id}/accept")
    @PreAuthorize("hasAnyAuthority('JOB:CREATE')")
    public ResponseEntity<?> accept(
            @PathVariable(name = "id") Long id,
            HttpServletRequest request
    ) {
        try {
            Offer offer = offerService.accept(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(offer);
        }
        catch (NotFound found) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                            "Offer {"+id+"} is not found !",
                            request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
