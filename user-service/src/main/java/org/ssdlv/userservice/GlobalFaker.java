package org.ssdlv.userservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.ssdlv.userservice.permissions.Permission;
import org.ssdlv.userservice.permissions.PermissionFaker;
import org.ssdlv.userservice.permissions.PermissionRepository;
import org.ssdlv.userservice.userpermission.UserPermission;
import org.ssdlv.userservice.userpermission.UserPermissionFaker;
import org.ssdlv.userservice.userpermission.UserPermissionRepository;
import org.ssdlv.userservice.users.User;
import org.ssdlv.userservice.users.UserFaker;
import org.ssdlv.userservice.users.UserRepository;

@Configuration
public class GlobalFaker {
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final UserPermissionRepository userPermissionRepository;
    private final PasswordEncoder passwordEncoder;

    public GlobalFaker(UserRepository userRepository, PermissionRepository permissionRepository, UserPermissionRepository userPermissionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.userPermissionRepository = userPermissionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean("global_faker")
    public CommandLineRunner start() {
        return args -> {
            //USERS
            UserFaker.userFaker(passwordEncoder, userRepository);

            //PERMISSIONS
            PermissionFaker.permissionFaker(permissionRepository);

            //USER PERMISSIONS
            UserPermissionFaker.userPermissionFaker(userPermissionRepository, userRepository, permissionRepository);
        };
    }
}
