package org.ssdlv.userservice.userpermission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ssdlv.userservice.users.User;

import java.util.List;

public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {
    List<UserPermission> findByUser(User user);
}
