package org.ssdlv.userservice.userpermission;

import org.ssdlv.userservice.permissions.PermissionRepository;
import org.ssdlv.userservice.users.UserRepository;

public class UserPermissionFaker {

    public static void userPermissionFaker(UserPermissionRepository userPermissionRepository, UserRepository userRepository, PermissionRepository permissionRepository) {
        userPermissionRepository.save(new UserPermission(userRepository.getOne(1L), permissionRepository.getOne(1L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(1L), permissionRepository.getOne(2L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(1L), permissionRepository.getOne(3L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(1L), permissionRepository.getOne(4L)));

        userPermissionRepository.save(new UserPermission(userRepository.getOne(1L), permissionRepository.getOne(9L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(1L), permissionRepository.getOne(10L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(1L), permissionRepository.getOne(11L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(1L), permissionRepository.getOne(12L)));

        userPermissionRepository.save(new UserPermission(userRepository.getOne(1L), permissionRepository.getOne(13L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(1L), permissionRepository.getOne(14L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(1L), permissionRepository.getOne(15L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(1L), permissionRepository.getOne(16L)));

        userPermissionRepository.save(new UserPermission(userRepository.getOne(1L), permissionRepository.getOne(17L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(1L), permissionRepository.getOne(18L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(1L), permissionRepository.getOne(19L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(1L), permissionRepository.getOne(10L)));

        //USER 2
        userPermissionRepository.save(new UserPermission(userRepository.getOne(2L), permissionRepository.getOne(1L)));

        userPermissionRepository.save(new UserPermission(userRepository.getOne(2L), permissionRepository.getOne(5L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(2L), permissionRepository.getOne(6L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(2L), permissionRepository.getOne(7L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(2L), permissionRepository.getOne(8L)));

        userPermissionRepository.save(new UserPermission(userRepository.getOne(2L), permissionRepository.getOne(9L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(2L), permissionRepository.getOne(10L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(2L), permissionRepository.getOne(11L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(2L), permissionRepository.getOne(12L)));

        userPermissionRepository.save(new UserPermission(userRepository.getOne(2L), permissionRepository.getOne(13L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(2L), permissionRepository.getOne(14L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(2L), permissionRepository.getOne(15L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(2L), permissionRepository.getOne(16L)));

        userPermissionRepository.save(new UserPermission(userRepository.getOne(2L), permissionRepository.getOne(17L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(2L), permissionRepository.getOne(18L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(2L), permissionRepository.getOne(19L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(2L), permissionRepository.getOne(10L)));

        //USER 2
        userPermissionRepository.save(new UserPermission(userRepository.getOne(3L), permissionRepository.getOne(1L)));

        userPermissionRepository.save(new UserPermission(userRepository.getOne(3L), permissionRepository.getOne(5L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(3L), permissionRepository.getOne(6L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(3L), permissionRepository.getOne(7L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(3L), permissionRepository.getOne(8L)));

        userPermissionRepository.save(new UserPermission(userRepository.getOne(3L), permissionRepository.getOne(9L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(3L), permissionRepository.getOne(10L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(3L), permissionRepository.getOne(11L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(3L), permissionRepository.getOne(12L)));

        userPermissionRepository.save(new UserPermission(userRepository.getOne(3L), permissionRepository.getOne(13L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(3L), permissionRepository.getOne(14L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(3L), permissionRepository.getOne(15L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(3L), permissionRepository.getOne(16L)));

        userPermissionRepository.save(new UserPermission(userRepository.getOne(3L), permissionRepository.getOne(17L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(3L), permissionRepository.getOne(18L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(3L), permissionRepository.getOne(19L)));
        userPermissionRepository.save(new UserPermission(userRepository.getOne(3L), permissionRepository.getOne(10L)));
    }
}

