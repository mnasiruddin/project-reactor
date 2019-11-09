package com.projectreactor.coding.search.session;

import com.projectreactor.coding.search.session.domain.SessionResponse;
import com.projectreactor.coding.search.session.service.SessionService;
import com.projectreactor.coding.search.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/session")
@Log4j2
public class SessionController {

  private final JwtUtil jwtUtil;
  private final SessionService sessionService;

  public SessionController(JwtUtil jwtUtil, SessionService sessionService) {
    this.jwtUtil = jwtUtil;
    this.sessionService = sessionService;
  }

  @GetMapping("/isLoggedIn")
  public Mono<Boolean> isLoggedIn(@RequestHeader Map<String, String> headers) {
    return sessionService.isActiveSession(jwtUtil.getFromHeader(headers));
  }

  @GetMapping("/name")
  public Mono<SessionResponse> name(@RequestHeader Map<String, String> headers) {
    return sessionService.getUsername(jwtUtil.getFromHeader(headers)).map(SessionResponse::new);

  }
}
