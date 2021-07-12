package org.ssdlv.categoryservice.jobs;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("JOB-SERVICE")
public interface JobService {

    @GetMapping("/jobs/{category}/count")
    int count_jobs_by_category(@PathVariable(name = "category") Long category);
}
