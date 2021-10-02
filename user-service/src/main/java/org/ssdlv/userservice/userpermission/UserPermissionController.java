package org.ssdlv.userservice.userpermission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.ssdlv.userservice.utils.forms.AffectUserPermissionRequest;
import javax.validation.Valid;

@Transactional
@CrossOrigin("*")
@RestController
public class UserPermissionController {
    Logger logger = LoggerFactory.getLogger(UserPermissionController.class);
    private final UserPermissionService userPermissionService;

    public UserPermissionController(UserPermissionService userPermissionService) {
        this.userPermissionService = userPermissionService;
    }

    @PreAuthorize("hasAnyAuthority('USER-PERMISSION:GRANT')")
    @PostMapping("/permissions/grantPermissionToUser")
    public ResponseEntity<UserPermission> grantPermissionToUser(@Valid @RequestBody AffectUserPermissionRequest request) {
        try {
            UserPermission userPermission = userPermissionService.grantPermissionToUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(userPermission);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('USER-PERMISSION:REVOKE')")
    @PostMapping("/permissions/revokePermissionToUser")
    public ResponseEntity<UserPermission> revokePermissionToUser(@Valid @RequestBody AffectUserPermissionRequest request) {
        try {
            UserPermission userPermission = userPermissionService.revokePermissionToUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(userPermission);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
