package pl.lodz.p.it.ssbd2023.ssbd03.auth;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pl.lodz.p.it.ssbd2023.ssbd03.config.Roles;
import pl.lodz.p.it.ssbd2023.ssbd03.entities.AccessLevelMapping;
import pl.lodz.p.it.ssbd2023.ssbd03.util.BcryptHashGenerator;
import pl.lodz.p.it.ssbd2023.ssbd03.util.LoadConfig;

import static java.lang.Long.parseLong;

public class JwtGenerator {

    private long timeout = parseLong(LoadConfig.loadSaltFromConfig("timeout"));
    private String secret = LoadConfig.loadSaltFromConfig("secret");

    public String generateJWT(String login, List<String> roles) {

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secret)
                .setSubject(login)
                .setIssuedAt(new Date())
                .claim("role", String.join(",", roles))
                .setExpiration(new Date(System.currentTimeMillis() + timeout))
                .compact();
    }

    public Jws<Claims> parseJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt);
    }
}
