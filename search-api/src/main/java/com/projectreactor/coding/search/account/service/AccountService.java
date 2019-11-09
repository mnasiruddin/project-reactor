package com.projectreactor.coding.search.account.service;

import com.projectreactor.coding.search.account.domain.Account;
import com.projectreactor.coding.search.account.domain.NewAccountRequest;
import reactor.core.publisher.Mono;

public interface AccountService {

  Mono<Account> insert(Account account);

  Mono<Account> findByUsername(String username);

  Mono<Account> insertFromInput(NewAccountRequest newAccountRequest);

}
