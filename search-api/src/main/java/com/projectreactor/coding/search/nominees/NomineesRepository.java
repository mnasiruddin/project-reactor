package com.projectreactor.coding.search.nominees;

import com.projectreactor.coding.search.nominees.domain.NomineesResponse;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface NomineesRepository extends ReactiveMongoRepository<NomineesResponse, Long> {

  Flux<NomineesResponse> findByYearGreaterThanEqual(Integer year);
}
