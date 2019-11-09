package com.projectreactor.coding.search.nominees.service;

import com.projectreactor.coding.search.exception.NomineesNotFoundException;
import com.projectreactor.coding.search.nominees.NomineesRepository;
import com.projectreactor.coding.search.nominees.domain.NomineesResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Log4j2
@Service
public class NomineesServiceImpl implements NomineesService {

  private final NomineesRepository nomineesRepository;

  public NomineesServiceImpl(NomineesRepository nomineesRepository) {
    this.nomineesRepository = nomineesRepository;
  }

  @Override
  public Flux<NomineesResponse> findByYearGreaterThanEqual(Integer year) {
    return nomineesRepository.findByYearGreaterThanEqual(year)
        .switchIfEmpty(Flux.error(new NomineesNotFoundException()));
  }

}
