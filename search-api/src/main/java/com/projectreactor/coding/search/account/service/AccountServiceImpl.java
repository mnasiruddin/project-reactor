package com.projectreactor.coding.search.account.service;

import com.projectreactor.coding.search.account.AccountRepository;
import com.projectreactor.coding.search.account.domain.Account;
import com.projectreactor.coding.search.account.domain.NewAccountRequest;
import com.projectreactor.coding.search.constant.UserRole;
import com.projectreactor.coding.search.exception.AccountCreationException;
import com.projectreactor.coding.search.exception.UsernameNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;

  public AccountServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Mono<Account> insert(Account account) {
    return accountRepository.insert(account);
  }

  @Override
  public Mono<Account> findByUsername(String accountname) {
    return accountRepository.findByUsername(accountname).switchIfEmpty(Mono.error(new UsernameNotFoundException()));
  }

  @Override
  public Mono<Account> insertFromInput(NewAccountRequest newAccountRequest) {
    Account account = new Account();
    account.setFirstname(newAccountRequest.getFirstname());
    account.setSurname(newAccountRequest.getSurname());
    account.setUsername(newAccountRequest.getUsername());
    account.setPassword(newAccountRequest.getPassword());
    account.setRoles(Arrays.asList(UserRole.USER));

    return insert(account)
        .map(account1 -> {
          log.debug("New Account created : {}", account1.getId());
          return account;
        })
        .doOnError(exception -> Mono.error(new AccountCreationException("error creating new account : {}" + exception.getMessage())));
  }
}
