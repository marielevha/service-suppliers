package org.ssdlv.userservice.blacktoken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackTokenRepository extends JpaRepository<BlackToken,  Long> {
}
