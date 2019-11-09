package com.projectreactor.coding.search.account;

import com.projectreactor.coding.search.account.domain.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, Long> {

  Mono<Account> findByUsername(String username);
}
