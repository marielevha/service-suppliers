package org.ssdlv.userservice.userpermission;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.stereotype.Service;
import org.ssdlv.userservice.permissions.Permission;
import org.ssdlv.userservice.permissions.PermissionRepository;
import org.ssdlv.userservice.users.User;
import org.ssdlv.userservice.users.UserRepository;
import org.ssdlv.userservice.utils.forms.AffectUserPermissionRequest;

import java.util.Date;

@Service
public class UserPermissionService {
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final UserPermissionRepository userPermissionRepository;

    public UserPermissionService(UserRepository userRepository, PermissionRepository permissionRepository, UserPermissionRepository userPermissionRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.userPermissionRepository = userPermissionRepository;
    }

    public UserPermission grantPermissionToUser(AffectUserPermissionRequest request) throws NotFound {
        User user = userRepository.findById(request.getUserId()).orElseThrow(NotFound::new);
        Permission permission = permissionRepository.findById(request.getPermissionId()).orElseThrow(NotFound::new);

        return userPermissionRepository.save(new UserPermission(user, permission));
    }

    public UserPermission revokePermissionToUser(AffectUserPermissionRequest request) throws NotFound {
        UserPermission userPermission = userPermissionRepository
                .findByUserIdAndPermissionId(request.getUserId(), request.getPermissionId());
        userPermission.setDeletedAt(new Date());
        return userPermissionRepository.save(userPermission);
    }
}
