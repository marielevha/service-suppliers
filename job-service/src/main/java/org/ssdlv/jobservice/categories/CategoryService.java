package org.ssdlv.jobservice.categories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("CATEGORY-SERVICE")
public interface CategoryService {
    @GetMapping("categories/{id}")
    Category findCategoryById(@PathVariable(name = "id") Long id);

    @GetMapping("data/count_category")
    int count_category(@RequestParam(name = "state") String state);
}
