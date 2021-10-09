package org.ssdlv.categoryservice.blacktoken;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("USER-SERVICE")
public interface BlackTokenService {
    @GetMapping("/blackListTokens")
    List<String> blackListTokens();
}
