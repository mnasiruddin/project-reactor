package com.projectreactor.coding.search.exception;

public class AccountCreationException extends RuntimeException {

  private static final ErrorCode ERROR_CODE = ErrorCode.UN_AUTHORIZED_ACCESS;

  /**
   * Constructs a <code>AccountCreationException</code> with the specified message.
   *
   */
  public AccountCreationException(String message) {
    super(message);
  }

  public ErrorCode errorCode() {
    return ERROR_CODE;
  }

}
