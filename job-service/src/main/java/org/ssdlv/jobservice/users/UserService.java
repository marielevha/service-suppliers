package org.ssdlv.jobservice.users;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("USER-SERVICE")
public interface UserService {
    @GetMapping("users/{id}")
    User findUSerById(@PathVariable(name = "id") Long id);

    @GetMapping("data/count_user")
    int count_user(
            @RequestParam(name = "profile", defaultValue = "all") String profile,
            @RequestParam(name = "state", defaultValue = "all") String state
    );
}
