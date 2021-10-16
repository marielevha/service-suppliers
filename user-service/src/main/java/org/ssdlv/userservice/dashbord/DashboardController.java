package org.ssdlv.userservice.dashbord;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ssdlv.userservice.users.UserService;

@Transactional
@RestController
public class DashboardController {
    Logger logger = LoggerFactory.getLogger(DashboardController.class);
    private final UserService userService;

    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Méthode permettant de compter le nombre d'utilisateur par profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content)
    })
    @GetMapping("data/count_user")
    public ResponseEntity<?> count_user(
        @RequestParam(name = "profile", defaultValue = "all") String profile,
        @RequestParam(name = "state", defaultValue = "all") String state
    ) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.count_user(profile, state));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
