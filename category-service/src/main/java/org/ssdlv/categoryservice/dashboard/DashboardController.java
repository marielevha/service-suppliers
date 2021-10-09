package org.ssdlv.categoryservice.dashboard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ssdlv.categoryservice.categories.CategoryService;

import java.util.HashMap;
import java.util.Map;

@Transactional
@RestController
public class DashboardController {

    private final CategoryService categoryService;

    public DashboardController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("data/count_category")
    public ResponseEntity<?> count_category(@RequestParam(name = "state", defaultValue = "all") String state) {
        Map<String, Object> data = new HashMap<>();
        data.put("count_category", categoryService.count_category(state));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
