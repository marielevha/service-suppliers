package org.ssdlv.jobservice.offers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(OfferController.class);
    private final OfferService offerService;
    private final OfferRepository offerRepository;

    public OfferController(OfferService offerService, OfferRepository offerRepository) {
        this.offerService = offerService;
        this.offerRepository = offerRepository;
    }

    @Operation(summary = "Méthode permettant de récupérer la liste des offres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content),
    })
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
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Méthode permettant de récupérer une offre par <<ID>>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Offer.class))),
    })
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
            logger.debug("Offer: {} is not found.", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                            "Offer {"+id+"} is not found !",
                            request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Méthode permettant de créer une nouvelle offre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Offer.class))),
    })
    @PostMapping("/offers")
    @PreAuthorize("hasAnyAuthority('OFFER:CREATE')")
    public ResponseEntity<Offer> create(@Valid @RequestBody Offer offer) {
        try {
            offer = offerService.create(offer);
            return ResponseEntity.status(HttpStatus.CREATED).body(offer);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Méthode permettant de mettre à jour une offre par <<ID>>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Offer.class))),
    })
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
            logger.debug("Offer: {} is not found.", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "Offer {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Méthode permettant de supprimer une offre par <<ID>>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content),
    })
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
            logger.debug("Offer: {} is not found.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (NotFound found) {
            logger.debug("Offer: {} is not found.", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                            "Offer {"+id+"} is not found !",
                            request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Méthode permettant d'accepter une offre par <<ID>>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Offer.class))),
    })
    @PostMapping("/offers/{id}/accept")
    @PreAuthorize("hasAnyAuthority('OFFER:CREATE')")
    public ResponseEntity<?> accept(
        @PathVariable(name = "id") Long id,
        HttpServletRequest request
    ) {
        try {
            Offer offer = offerService.accept(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(offer);
        }
        catch (NotFound found) {
            logger.debug("Offer: {} is not found.", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "Offer {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
