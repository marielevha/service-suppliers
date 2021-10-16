package org.ssdlv.categoryservice.tags;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(exported = false)
public interface TagRepository extends JpaRepository<Tag, Long> {
}