package org.ssdlv.jobservice.cities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class CityController {
    Logger logger = LoggerFactory.getLogger(CityController.class);
    private final CityRepository cityRepository;

    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping("/cities")
    //@PreAuthorize("hasAnyAuthority('CITIE:READ')")
    public ResponseEntity<?> find_all_active() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cityRepository.findAllActiveCities());
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
