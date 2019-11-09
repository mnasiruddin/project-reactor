package com.projectreactor.coding.search.exception;

public enum ErrorCode {

  USER_NAME_ALREADY_EXISTS("Username already exists"),
  USER_NAME_NOT_FOUND("Username not found"),
  UN_AUTHORIZED_ACCESS("Invalid combination of credentials"),
  ACCOUNT_CREATION_UNSUCCESFUL("Unable to create New Account"),
  INVALID_TOKEN("Token is either expired or is invalid"),
  INVALID_SESSION("Session is expired"),
  NOMINEES_NOT_FOUND("No Nominees found"),
  ERROR("error occured");


  private String errorMessage;
  ErrorCode(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String errorMessage() {
    return errorMessage;
  }
}
