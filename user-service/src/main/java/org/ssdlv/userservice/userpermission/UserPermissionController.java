package org.ssdlv.userservice.userpermission;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.ssdlv.userservice.permissions.Permission;
import org.ssdlv.userservice.permissions.PermissionRepository;
import org.ssdlv.userservice.users.User;
import org.ssdlv.userservice.users.UserRepository;
import org.ssdlv.userservice.utils.forms.AffectUserPermissionRequest;

import javax.validation.Valid;

@Transactional
@CrossOrigin("*")
@RestController
public class UserPermissionController {

    private UserRepository userRepository;
    private PermissionRepository permissionRepository;
    private UserPermissionRepository userPermissionRepository;

    public UserPermissionController(
            UserRepository userRepository,
            PermissionRepository permissionRepository,
            UserPermissionRepository userPermissionRepository
    ) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.userPermissionRepository = userPermissionRepository;
    }

    @PostMapping("/permissions")
    public ResponseEntity<UserPermission> grantPermissionToUser(@Valid @RequestBody AffectUserPermissionRequest request) {
        try {
            User user = userRepository.findById(request.getUserId()).orElseThrow(null);
            Permission permission = permissionRepository.findById(request.getPermissionId()).orElseThrow(null);

            if (user == null || permission == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            UserPermission userPermission = userPermissionRepository.save(new UserPermission(user, permission));
            return ResponseEntity.status(HttpStatus.CREATED).body(userPermission);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/permissions/")
    public ResponseEntity<UserPermission> revokePermissionToUser(@Valid @RequestBody AffectUserPermissionRequest request) {
        try {
            User user = userRepository.findById(request.getUserId()).orElseThrow(null);
            Permission permission = permissionRepository.findById(request.getPermissionId()).orElseThrow(null);

            if (user == null || permission == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            UserPermission userPermission = userPermissionRepository.save(
                    new UserPermission(user, permission)
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(userPermission);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
