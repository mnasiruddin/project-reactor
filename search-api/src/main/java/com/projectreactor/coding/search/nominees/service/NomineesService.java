package com.projectreactor.coding.search.nominees.service;

import com.projectreactor.coding.search.nominees.domain.NomineesResponse;
import reactor.core.publisher.Flux;

public interface NomineesService {

  Flux<NomineesResponse> findByYearGreaterThanEqual(Integer year);

}
