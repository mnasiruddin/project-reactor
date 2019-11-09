package com.projectreactor.coding.search.nominees;

import com.projectreactor.coding.search.nominees.domain.NomineesResponse;
import com.projectreactor.coding.search.nominees.service.NomineesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Log4j2
@RequestMapping("/api/v1/oscar")
public class NomineesController {

  private final NomineesService nomineesService;

  public NomineesController(NomineesService nomineesService) {
    this.nomineesService = nomineesService;
  }

  @GetMapping(value = "/search/{year}")
  public Flux<NomineesResponse> search(@PathVariable int year) {
    return nomineesService.findByYearGreaterThanEqual(year);
  }
}
