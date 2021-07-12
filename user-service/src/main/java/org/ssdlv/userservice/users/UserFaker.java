package org.ssdlv.userservice.users;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.ssdlv.userservice.permissions.PermissionRepository;
import org.ssdlv.userservice.userpermission.UserPermission;
import org.ssdlv.userservice.userpermission.UserPermissionRepository;

@Configuration
public class UserFaker {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PermissionRepository permissionRepository;
    private final UserPermissionRepository userPermissionRepository;

    public UserFaker(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            PermissionRepository permissionRepository,
            UserPermissionRepository userPermissionRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.permissionRepository = permissionRepository;
        this.userPermissionRepository = userPermissionRepository;
    }

    @Bean("user_faker")
    @DependsOn({"password_encoder", "permission_faker"})
    public CommandLineRunner start() {
        return args -> {
            String password = passwordEncoder.encode("password");
            userRepository.save(new User("Carols", "DENVER", "000000000", "denver@ssdlv.com", password, null,null));
            userRepository.save(new User("Jessica", "JONES", "000000000", "jones@ssdlv.com", password, null, null));
            userRepository.save(new User("Pepper", "POTTS", "000000000", "potts@ssdlv.com", password, null, null));

            userPermissionRepository.save(new UserPermission(userRepository.getOne(1L),permissionRepository.getOne(1L)));
            userPermissionRepository.save(new UserPermission(userRepository.getOne(1L),permissionRepository.getOne(2L)));
            userPermissionRepository.save(new UserPermission(userRepository.getOne(1L),permissionRepository.getOne(3L)));
            userPermissionRepository.save(new UserPermission(userRepository.getOne(1L),permissionRepository.getOne(4L)));

            userPermissionRepository.save(new UserPermission(userRepository.getOne(2L),permissionRepository.getOne(5L)));
            userPermissionRepository.save(new UserPermission(userRepository.getOne(2L),permissionRepository.getOne(6L)));
            userPermissionRepository.save(new UserPermission(userRepository.getOne(2L),permissionRepository.getOne(7L)));
            userPermissionRepository.save(new UserPermission(userRepository.getOne(2L),permissionRepository.getOne(8L)));

            userPermissionRepository.save(new UserPermission(userRepository.getOne(1L),permissionRepository.getOne(9L)));
            userPermissionRepository.save(new UserPermission(userRepository.getOne(1L),permissionRepository.getOne(10L)));
            userPermissionRepository.save(new UserPermission(userRepository.getOne(1L),permissionRepository.getOne(11L)));
            userPermissionRepository.save(new UserPermission(userRepository.getOne(1L),permissionRepository.getOne(12L)));

            userPermissionRepository.save(new UserPermission(userRepository.getOne(1L),permissionRepository.getOne(13L)));
            userPermissionRepository.save(new UserPermission(userRepository.getOne(1L),permissionRepository.getOne(14L)));
            userPermissionRepository.save(new UserPermission(userRepository.getOne(1L),permissionRepository.getOne(15L)));
            userPermissionRepository.save(new UserPermission(userRepository.getOne(1L),permissionRepository.getOne(16L)));

        };
    }
}
