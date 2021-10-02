package org.ssdlv.jobservice.jobs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.PagedModel;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface JobRepository extends JpaRepository<Job, Long> {
    Optional<Job> findFirstBySlug(String slug);
    Page<Job> findAllByPublishedAtIsNull(Pageable pageable);
    Page<Job> findAllByPublishedAtIsNotNull(Pageable pageable);

    List<Job> findAllByPublishedAtBetween(LocalDate start, LocalDate end);

    List<Job> findAllByCategoryIdAndDeletedAtIsNullAndPublishedAtIsNotNull(Long id);
    
    //List<Job> findAllByPublishedAtIsNull
}
