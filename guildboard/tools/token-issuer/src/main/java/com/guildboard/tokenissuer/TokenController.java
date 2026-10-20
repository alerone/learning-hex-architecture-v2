package com.guildboard.tokenissuer;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.jwk.JWKSet;

/**
 * TokenController
 */
@RestController 
public class TokenController {

    public record TokenRequest(String username, String password) {}

    private final JwtEncoder encoder;
    private final JWKSet jwkSet;
    private final IssuerProperties properties;

    public TokenController(JwtEncoder encoder, JWKSet jwkSet, IssuerProperties properties) {
        this.encoder = encoder;
        this.jwkSet = jwkSet;
        this.properties = properties;
    }

    @PostMapping("/token")
    public ResponseEntity<Map<String,Object>> token(@RequestBody TokenRequest request) {
        Optional<IssuerProperties.User> found = properties.users()
            .stream()
            .filter(u -> u.username().equals(request.username()) && u.password().equals(request.password()))
            .findFirst();
        if (found.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        IssuerProperties.User user = found.get();
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer(properties.issuer())
            .subject(user.subject())
            .issuedAt(now)
            .expiresAt(now.plus(properties.ttl()))
            .claim("roles", List.of(user.role()))
            .build();

        JwsHeader header = JwsHeader.with(SignatureAlgorithm.RS256)
            .keyId(jwkSet.getKeys().get(0).getKeyID())
            .build();
        String token = encoder 
            .encode(JwtEncoderParameters.from(header, claims))
            .getTokenValue();
        return ResponseEntity.ok(Map.of(
                    "access_token", token,
                    "token_type", "Bearer",
                    "expires_in", properties.ttl().toSeconds()));
    }

    
}
