package com.projectreactor.coding.search.exception;

public class InvalidTokenException extends RuntimeException {

  private static final ErrorCode ERROR_CODE = ErrorCode.INVALID_TOKEN;

  /**
   * Constructs a <code>InvalidTokenException</code> with the specified message.
   *
   */
  public InvalidTokenException() {
    super(ERROR_CODE.errorMessage());
  }

  public ErrorCode errorCode() {
    return ERROR_CODE;
  }

}
