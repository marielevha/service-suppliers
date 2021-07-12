package org.ssdlv.userservice.userpermission;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.ssdlv.userservice.permissions.PermissionRepository;
import org.ssdlv.userservice.users.User;
import org.ssdlv.userservice.users.UserRepository;

@Configuration
public class UserPermissionFaker {
    private UserPermissionRepository userPermissionRepository;
    private UserRepository userRepository;
    private PermissionRepository permissionRepository;

    public UserPermissionFaker(
            UserPermissionRepository userPermissionRepository,
            UserRepository userRepository,
            PermissionRepository permissionRepository
    ) {
        this.userPermissionRepository = userPermissionRepository;
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    //@Bean("user_permission_faker")
    //@DependsOn(value = {"password_encoder", "user_faker","permission_faker"})
    public CommandLineRunner start() {
        return args -> {
            userPermissionRepository.save(new UserPermission(userRepository.getOne(1L),permissionRepository.getOne(1L)));
            userPermissionRepository.save(new UserPermission(userRepository.getOne(1L),permissionRepository.getOne(2L)));
            userPermissionRepository.save(new UserPermission(userRepository.getOne(1L),permissionRepository.getOne(3L)));
            userPermissionRepository.save(new UserPermission(userRepository.getOne(1L),permissionRepository.getOne(4L)));

            userPermissionRepository.save(new UserPermission(userRepository.getOne(2L),permissionRepository.getOne(5L)));
            userPermissionRepository.save(new UserPermission(userRepository.getOne(2L),permissionRepository.getOne(6L)));
            userPermissionRepository.save(new UserPermission(userRepository.getOne(2L),permissionRepository.getOne(7L)));
            userPermissionRepository.save(new UserPermission(userRepository.getOne(2L),permissionRepository.getOne(8L)));
        };
    }
}
