package org.ssdlv.userservice.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
