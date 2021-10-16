package org.ssdlv.userservice.permissions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(exported = false)
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
