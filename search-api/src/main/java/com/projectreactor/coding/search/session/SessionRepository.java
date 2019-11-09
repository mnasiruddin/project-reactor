package com.projectreactor.coding.search.session;

import com.projectreactor.coding.search.session.domain.SessionDao;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface SessionRepository extends ReactiveMongoRepository<SessionDao, Long> {

  Mono<SessionDao> findByTokenidentifier(String tokenidentifier);

  default Mono<Boolean> isActiveSession(String tokenidentifier) {
    return findByTokenidentifier(tokenidentifier)
        .map(sessionDao -> true)
        .defaultIfEmpty(false);
  }

}
