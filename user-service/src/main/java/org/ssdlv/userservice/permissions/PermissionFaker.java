package org.ssdlv.userservice.permissions;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class PermissionFaker {
    private PermissionRepository permissionRepository;

    public PermissionFaker(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Bean("permission_faker")
    public CommandLineRunner start() {
        return args -> {
            permissionRepository.save(new Permission("CATEGORY:READ"));
            permissionRepository.save(new Permission("CATEGORY:UPDATE"));
            permissionRepository.save(new Permission("CATEGORY:DELETE"));
            permissionRepository.save(new Permission("CATEGORY:CREATE"));

            permissionRepository.save(new Permission("TAG:READ"));
            permissionRepository.save(new Permission("TAG:UPDATE"));
            permissionRepository.save(new Permission("TAG:DELETE"));
            permissionRepository.save(new Permission("TAG:CREATE"));

            permissionRepository.save(new Permission("JOB:READ"));
            permissionRepository.save(new Permission("JOB:UPDATE"));
            permissionRepository.save(new Permission("JOB:DELETE"));
            permissionRepository.save(new Permission("JOB:CREATE"));

            permissionRepository.save(new Permission("USER:READ"));
            permissionRepository.save(new Permission("USER:UPDATE"));
            permissionRepository.save(new Permission("USER:DELETE"));
            permissionRepository.save(new Permission("USER:CREATE"));

        };
    }
}
