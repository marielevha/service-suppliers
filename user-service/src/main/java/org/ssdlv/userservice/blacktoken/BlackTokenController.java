package org.ssdlv.userservice.blacktoken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class BlackTokenController {
    Logger logger = LoggerFactory.getLogger(BlackTokenController.class);
    private final BlackTokenService blackTokenService;

    public BlackTokenController(BlackTokenService blackTokenService) {
        this.blackTokenService = blackTokenService;
    }

    @GetMapping("/blackListTokens")
    public ResponseEntity<?> blackListTokens() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(blackTokenService.blackListTokens());
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
