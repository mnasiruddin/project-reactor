package com.projectreactor.coding.search.activity.service;

import com.projectreactor.coding.search.activity.domain.ActivityRequest;
import com.projectreactor.coding.search.activity.domain.ActivityResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class ActivityServiceImpl implements ActivityService {

  @Override
  public Mono<ActivityResponse> findPosts(String userId, String postId) {
    return ActivityService.webClient()
            .get()
            .uri(uriBuilder -> {
              if (!StringUtils.isEmpty(userId)) {
                return uriBuilder.path("/posts/" + postId).queryParam("userId", userId).build();
              } else {
                return uriBuilder.path("/posts/" + postId).build();
              }
            })
            .retrieve()
            .bodyToMono(ActivityResponse.class)
            .onErrorMap(throwable -> {
              log.error(throwable);
              return throwable;
            })
            .onErrorReturn(new ActivityResponse());
  }

  @Override
  public Mono<ActivityResponse> createPosts(ActivityRequest request) {
    return ActivityService.webClient()
            .post()
            .uri(uriBuilder -> uriBuilder.path("/posts").build())
            .syncBody(request)
            .retrieve()
            .bodyToMono(ActivityResponse.class)
            .onErrorReturn(new ActivityResponse());
  }
}
