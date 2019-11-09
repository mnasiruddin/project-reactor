package com.projectreactor.coding.search.account;

import com.projectreactor.coding.search.account.domain.AuthRequest;
import com.projectreactor.coding.search.account.domain.NewAccountRequest;
import com.projectreactor.coding.search.account.domain.NewAccountResponse;
import com.projectreactor.coding.search.account.service.AccountService;
import com.projectreactor.coding.search.exception.UnAuthorizeException;
import com.projectreactor.coding.search.exception.UsernameAlreadyExistsException;
import com.projectreactor.coding.search.exception.UsernameNotFoundException;
import com.projectreactor.coding.search.session.service.SessionService;
import com.projectreactor.coding.search.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController()
@RequestMapping("/api/v1/auth")
@Log4j2
public class AccountController {

  private final JwtUtil jwtUtil;

  private final PasswordEncoder passwordEncoder;

  private final AccountService accountService;

  private final SessionService sessionService;

  public AccountController(JwtUtil jwtUtil,
      PasswordEncoder passwordEncoder, AccountService accountService, SessionService sessionService) {
    this.jwtUtil = jwtUtil;
    this.passwordEncoder = passwordEncoder;
    this.accountService = accountService;
    this.sessionService = sessionService;
  }

  @PostMapping(value = "/login")
  public Mono<ResponseEntity<Object>> login(@RequestBody AuthRequest authRequest) {
    log.debug("login with username : {}", authRequest.getUsername());
    return accountService.findByUsername(authRequest.getUsername()).flatMap(account -> {
      if (passwordEncoder.matches(authRequest.getPassword(), account.getPassword())) {
        final var token = jwtUtil.generateTokenWithPrefix(account);
        return sessionService.transform(token)
            .flatMap(sessionDao -> sessionService.save(sessionDao).thenReturn(ResponseEntity.ok().header(jwtUtil.getJwtConfig().getHeader(), token).build()));
      } else {
        return Mono.error(new UnAuthorizeException());
      }
    }).switchIfEmpty(Mono.error(new UsernameNotFoundException()));
  }

  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Object> create(@Valid @RequestBody NewAccountRequest newAccountRequest) {
    log.debug("create New Account with user : {}", newAccountRequest);

    return accountService.findByUsername(newAccountRequest.getUsername())
        .flatMap(account -> Mono.error(new UsernameAlreadyExistsException()))
        .onErrorResume(throwable -> {
                if (throwable instanceof UsernameNotFoundException) {
                  newAccountRequest.setPassword(passwordEncoder.encode(newAccountRequest.getPassword()));
                  return accountService.insertFromInput(newAccountRequest)
                      .map(account -> new NewAccountResponse(account.getId()));
                }
                return Mono.error(throwable);
        });
  }

}
