package org.ssdlv.userservice.blacktoken;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlackTokenService {
    private final BlackTokenRepository blackTokenRepository;

    public BlackTokenService(BlackTokenRepository blackTokenRepository) {
        this.blackTokenRepository = blackTokenRepository;
    }

    public List<String> blackListTokens() {
        List<String> blackListTokens = new ArrayList<>();

        List<BlackToken> tokens = blackTokenRepository.findAll();
        tokens.forEach(blackToken -> {
            blackListTokens.add(blackToken.getToken());
        });
        return blackListTokens;
    }
}
