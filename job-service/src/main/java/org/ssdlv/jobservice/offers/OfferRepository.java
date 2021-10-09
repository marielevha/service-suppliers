package org.ssdlv.jobservice.offers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.ssdlv.jobservice.jobs.Job;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    Page<Offer> findAllByDeletedAtIsNullAndJobMatches(Pageable pageable, Job job);
}
