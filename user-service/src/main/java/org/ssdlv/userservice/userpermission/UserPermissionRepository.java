package org.ssdlv.userservice.userpermission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.ssdlv.userservice.users.User;

import java.util.List;

@RestResource(exported = false)
public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {
    List<UserPermission> findByUser(User user);
    UserPermission findByUserIdAndPermissionId(Long userId, Long permissionId);
}
