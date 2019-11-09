package com.projectreactor.coding.search.account.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class  AuthRequest {
  @NonNull
  private String username;
  @NonNull
  private String password;
}