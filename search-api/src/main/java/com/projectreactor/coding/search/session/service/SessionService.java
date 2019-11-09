package com.projectreactor.coding.search.session.service;

import com.projectreactor.coding.search.session.domain.SessionDao;
import reactor.core.publisher.Mono;

public interface SessionService {

  Mono<Boolean> isActiveSession(String token);

  Mono<SessionDao> findByTokenidentifier(String tokenidentifier);

  Mono<SessionDao> save(SessionDao tokenDao);

  Mono<SessionDao> transform(String token);

  Mono<String> getUsername(String token);
}
