package org.ssdlv.userservice.blacktoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(exported = false)
public interface BlackTokenRepository extends JpaRepository<BlackToken,  Long> {
}
