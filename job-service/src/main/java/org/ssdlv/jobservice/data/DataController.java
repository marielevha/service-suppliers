package org.ssdlv.jobservice.data;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ssdlv.jobservice.categories.CategoryService;
import org.ssdlv.jobservice.jobs.Job;
import org.ssdlv.jobservice.jobs.JobService;
import org.ssdlv.jobservice.users.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@RestController
public class DataController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final JobService jobService;

    public DataController(UserService userService, CategoryService categoryService, JobService jobService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.jobService = jobService;
    }

    @GetMapping("/data")
    public ResponseEntity<?> count(HttpServletResponse response) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("clients_total", 10);
        data.put("providers_total", 15);
        data.put("admins_total", 2);

        data.put("jobs_total", 5);

        System.err.println("COUNT CAT : " + categoryService.count_category("all"));

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("data/count_job")
    public ResponseEntity<?> count_job(@RequestParam(name = "status", defaultValue = "all") String status) {
        Map<String, Object> data = new HashMap<>();
        data.put("count_job", jobService.count_job(status));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("data/job_daily_published")
    public ResponseEntity<?> job_daily_published(
            @RequestParam(name = "deleted", defaultValue = "false") boolean deleted
    ) {
        LocalDate start = LocalDate.of(2021, 7, 1);
        LocalDate end = LocalDate.now();
        Map<LocalDate, Integer> data = jobService.job_daily_published(start, end, deleted);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
