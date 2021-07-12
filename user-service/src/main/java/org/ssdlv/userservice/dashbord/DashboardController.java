package org.ssdlv.userservice.dashbord;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ssdlv.userservice.users.UserService;

@Transactional
@RestController
public class DashboardController {
    private final UserService userService;

    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("data/count_user")
    public int count_user(
            @RequestParam(name = "profile", defaultValue = "all") String profile,
            @RequestParam(name = "state", defaultValue = "all") String state
    ) {
        return userService.count_user(profile, state);
    }
}
