package org.ssdlv.jobservice.cities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class CityController {
    private final CityRepository cityRepository;

    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping("/cities")
    //@PreAuthorize("hasAnyAuthority('CITIE:READ')")
    public ResponseEntity<?> find_all_active() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cityRepository.findAllActiveCities());
    }
}
