package org.ssdlv.jobservice.tags;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RestResource(exported = false)
public interface TagRepository extends JpaRepository<Tag, Long> {
    Page<Tag> findAllByTextContaining(String query, Pageable pageable);
    List<Tag> findAllByTextContaining(String query);
}
