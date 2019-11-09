package com.projectreactor.coding.search.activity;

import com.projectreactor.coding.search.activity.domain.ActivityRequest;
import com.projectreactor.coding.search.activity.domain.ActivityRequestDTO;
import com.projectreactor.coding.search.activity.domain.ActivityResponse;
import com.projectreactor.coding.search.activity.service.ActivityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Map;

@RestController()
@RequestMapping("/api/v1/posts")
@Log4j2
public class ActivityController {

  private final ActivityService accountService;

  public ActivityController(ActivityService accountService) {
    this.accountService = accountService;
  }

  @GetMapping(value = "/{postId}")
  public Mono<ActivityResponse> findPosts(@PathVariable("postId") String postId, @RequestParam Map<String, String> params) {
    log.debug("find posts by id : {}", postId);

    String userId = params.get("userId");
    return accountService.findPosts(userId, postId);
  }

  @GetMapping
  public Flux<ActivityResponse> findAllPosts(@RequestParam Map<String, String> params) {
    log.debug("find all posts");
    String userId = params.get("userId");
    return accountService.findAllPosts(userId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<ActivityResponse> create(@Valid @RequestBody ActivityRequestDTO activityRequest) {
    log.debug("create New Posts : {}", activityRequest);
    ActivityRequest activityRequest1 = new ActivityRequest();
    activityRequest1.setBody(activityRequest.getBody());
    activityRequest1.setTitle(activityRequest.getTitle());
    activityRequest1.setUserId(activityRequest.getUserId());
    return accountService.createPosts(activityRequest1);
  }

}
