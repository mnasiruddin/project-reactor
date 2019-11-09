package com.projectreactor.coding.search.security;

import static reactor.core.publisher.Mono.fromRunnable;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfiguration {

  private AuthenticationManager authenticationManager;
  private SecurityContextRepository securityContextRepository;

  public WebSecurityConfiguration(
      AuthenticationManager authenticationManager,
      SecurityContextRepository securityContextRepository) {
    this.authenticationManager = authenticationManager;
    this.securityContextRepository = securityContextRepository;
  }

  private static Mono<Void> commence(ServerWebExchange swe, AuthenticationException exception) {
    return fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
  }

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    return http
        .exceptionHandling()
        .authenticationEntryPoint(WebSecurityConfiguration::commence)
        .accessDeniedHandler((swe, e) -> fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
        .and()
        .csrf().disable()
        .formLogin().disable()
        .httpBasic().disable()
        .authenticationManager(authenticationManager)
        .securityContextRepository(securityContextRepository)
        .authorizeExchange()
        .pathMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
        .pathMatchers(HttpMethod.GET, "/api/v1/oscar/search/**").authenticated()
        .pathMatchers(HttpMethod.POST, "/api/v1/auth/signup").permitAll()
        .pathMatchers(HttpMethod.GET, "/api/v1/session/**").authenticated()
        .pathMatchers(HttpMethod.GET, "/info").permitAll()
         .pathMatchers(HttpMethod.GET, "/health").permitAll()
            .pathMatchers("*", "/api/v1/posts").permitAll()
            .pathMatchers("*", "/api/v1/posts/**").permitAll()
        .anyExchange().authenticated()
        .and().build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
