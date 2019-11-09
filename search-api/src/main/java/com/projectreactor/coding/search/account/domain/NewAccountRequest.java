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
public class NewAccountRequest {

  @NonNull
  String firstname;
  @NonNull
  String surname;
  @NonNull
  String username;
  @NonNull
  String password;
}
