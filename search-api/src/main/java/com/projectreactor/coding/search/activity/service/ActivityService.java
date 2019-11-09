package com.projectreactor.coding.search.activity.service;

import com.projectreactor.coding.search.activity.domain.ActivityRequest;
import com.projectreactor.coding.search.activity.domain.ActivityResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ActivityService {

  Mono<ActivityResponse> findPosts(String userId, String postId);

  default Flux<ActivityResponse> findAllPosts(String userId) {
    return webClient().get().uri(uriBuilder -> {
              if (!StringUtils.isEmpty(userId)) {
                return uriBuilder.path("/posts").queryParam("userId", userId).build();
              } else {
                return uriBuilder.path("/posts").build();
              }
            })
            .retrieve()
            .bodyToFlux(ActivityResponse.class);
  }

  Mono<ActivityResponse> createPosts(ActivityRequest request);

  static WebClient webClient() {
    return WebClient.create("https://jsonplaceholder.typicode.com");
  }
}
