package com.projectreactor.coding.search.activity.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ActivityRequestDTO {

  @NonNull
  String title;
  @NonNull
  String body;
  int userId;
}
