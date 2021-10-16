package org.ssdlv.jobservice.cities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RestResource(exported = false)
public interface CityRepository extends JpaRepository<City, Long> {
    @Query(value = "FROM City c WHERE c.deletedAt IS NULL ")
    List<City> findAllActiveCities();
}
