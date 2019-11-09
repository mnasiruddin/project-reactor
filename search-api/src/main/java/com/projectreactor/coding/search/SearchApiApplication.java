package com.projectreactor.coding.search;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableMongoAuditing
public class SearchApiApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(SearchApiApplication.class)
        .web(WebApplicationType.REACTIVE).run(args);
  }

}
