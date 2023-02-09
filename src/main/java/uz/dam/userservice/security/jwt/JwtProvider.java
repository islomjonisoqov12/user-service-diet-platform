package uz.dam.userservice.security.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import uz.dam.userservice.entities.RefreshToken;
import uz.dam.userservice.entities.enums.ApplicationType;
import uz.dam.userservice.repositories.RefreshTokenRepository;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Component for wor with JWT token.
 */
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final Logger log = LoggerFactory.getLogger(JwtProvider.class);
//    private final JwtParser jwtParser;

    private static final String AUTHORITIES_KEY = "auth";
    private static final String INVALID_JWT_TOKEN = "Invalid JWT token.";
    @Value("${jwt.access.secret}")
    private String jwtAccessSecret;
    @Value("${jwt.refresh.secret}")
    private String jwtRefreshSecret;
    @Value("${jwt.access.expire}")
    private Long jwtAccessExpire;
    @Value("${jwt.refresh.expire}")
    private Long jwtRefreshExpire;

    /**
     * bi function interface for create lambda expression method
     * @param list and string
     * @return joined string with input param join
     */
    public static BiFunction<List<String>, String, String> listJoinWithString = (list, join) -> {
        StringBuilder res = new StringBuilder();
        int size = list.size();
        if(size<1) {
            return "";
        }
        String last = list.remove(size-1);
        list.forEach(s -> res.append(s).append(join));
        res.append(last);
        return res.toString();
    };

    /**
     * function interface for get list authorities from authentication
     * return list string
     */
    public static Function<Authentication, List<String>> authenticationListString =
            authentication ->
                    authentication
                    .getAuthorities()
                            .stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList());
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * generate access token for user
     * @param authentication user session
     * @return generated token
     */
    public String generateAccessToken(Authentication authentication) {

        String authorities =
                listJoinWithString.apply(authenticationListString.apply(authentication), ",");
        Date date = new Date(System.currentTimeMillis()+jwtAccessExpire);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS512, jwtAccessSecret)
                .setExpiration(date)
                .compact();
    }
    public String generateRefreshToken(Authentication authentication, ApplicationType type) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        Date date = new Date(System.currentTimeMillis()+jwtRefreshExpire);
        String token = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS512, jwtRefreshSecret)
                .setExpiration(date)
                .compact();
        RefreshToken refreshToken;
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByTypeAndCreatedBy(type, authentication.getName());
        // if refresh token already exists in refresh tokens entity just update refresh token column
        if (optionalRefreshToken.isPresent()) {
            refreshToken = optionalRefreshToken.get();
            refreshToken.setRefreshToken(token);
        }else {
            // else create a new jwt token
            refreshToken = new RefreshToken(token, type);
        }
        refreshTokenRepository.save(refreshToken);
        return token;
    }
    public boolean validateToken(String authToken) {

//        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        try {
            Jwts.parser().setSigningKey(jwtAccessSecret).parseClaimsJws(authToken);
//            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {

            log.trace(INVALID_JWT_TOKEN, e);
        }
        return false;
    }
    public boolean validateRefreshToken(String authToken) {

//        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        try {
            Jwts.parser().setSigningKey(jwtRefreshSecret).parseClaimsJws(authToken);
//            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {

            log.trace(INVALID_JWT_TOKEN, e);
        }
        return false;
    }

    public Authentication getAuthentication(String token) {

        Claims claims = Jwts.parser().setSigningKey(jwtAccessSecret).parseClaimsJws(token).getBody();

//        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
