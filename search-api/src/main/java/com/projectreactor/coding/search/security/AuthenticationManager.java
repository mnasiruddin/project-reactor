package com.projectreactor.coding.search.security;

import com.projectreactor.coding.search.util.JwtUtil;
import com.projectreactor.coding.search.constant.UserRole;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

  private JwtUtil jwtUtil;

  public AuthenticationManager(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String token = authentication.getCredentials().toString();

    try {

      String username = jwtUtil.getUsernameFromToken(token);

      if (null != username && jwtUtil.validateToken(token)) {
        var claims = jwtUtil.getAllClaimsFromToken(token);

        @SuppressWarnings("unchecked")
        List<String> rolesMap = claims.get("role").as(List.class);
        List<UserRole> roles = new ArrayList<>();
        for (String rolemap : rolesMap) {
          roles.add(UserRole.valueOf(rolemap));
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            username,
            null,
            roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(
                Collectors.toList()));

        return Mono.just(auth);
      } else {
        return Mono.empty();
      }
    } catch (ExpiredJwtException exception) {
      log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
    } catch (UnsupportedJwtException exception) {
      log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
    } catch (MalformedJwtException exception) {
      log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
    } catch (SignatureException exception) {
      log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
    } catch (IllegalArgumentException exception) {
      log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
    }
    return Mono.empty();
  }

}
