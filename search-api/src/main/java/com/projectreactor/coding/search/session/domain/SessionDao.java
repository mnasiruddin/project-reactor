package com.projectreactor.coding.search.session.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("session")
public class SessionDao {

  @Id
  private String id;
  private String token;
  private String tokenidentifier;
}
