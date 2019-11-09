package com.projectreactor.coding.search.session.service;

import com.projectreactor.coding.search.exception.InvalidTokenException;
import com.projectreactor.coding.search.session.SessionRepository;
import com.projectreactor.coding.search.session.domain.SessionDao;
import com.projectreactor.coding.search.util.JwtUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SessionServiceImpl implements SessionService {

  private final SessionRepository sessionRepository;
  private final JwtUtil jwtUtil;

  public SessionServiceImpl(SessionRepository sessionRepository, JwtUtil jwtUtil) {
    this.sessionRepository = sessionRepository;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Mono<Boolean> isActiveSession(String token) {
    final String tokenIdentifier = getTokenIdentifier(token);
    return sessionRepository.isActiveSession(tokenIdentifier);
  }

  private String getTokenIdentifier(String token) {
    return jwtUtil.getDecodedJwt(token).getClaim(jwtUtil.getJwtConfig().getIdentifier())
          .asString();
  }

  @Override
  public Mono<SessionDao> findByTokenidentifier(String tokenidentifier) {
    return sessionRepository.findByTokenidentifier(tokenidentifier).switchIfEmpty(Mono.error(new InvalidTokenException()));
  }

  @Override
  public Mono<SessionDao> save(SessionDao sessionDao) {
    return sessionRepository.save(sessionDao);
  }

  @Override
  public Mono<String> getUsername(String token) {
    final var decodedJwt = jwtUtil.getDecodedJwt(token);
    return sessionRepository.findByTokenidentifier(decodedJwt.getClaim(jwtUtil.getJwtConfig().getIdentifier())
        .asString()).map(sessionDao -> decodedJwt.getSubject());
  }

  @Override
  public Mono<SessionDao> transform(String token) {
    SessionDao sessionDao = new SessionDao();
    sessionDao.setToken(token);
    sessionDao.setTokenidentifier(jwtUtil.getDecodedJwt(token).getClaim(jwtUtil.getJwtConfig().getIdentifier()).asString());
    return Mono.just(sessionDao);
  }

}
