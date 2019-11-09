package com.projectreactor.coding.search.exception;

public class InvalidSessionException extends RuntimeException {

  private static final ErrorCode ERROR_CODE = ErrorCode.UN_AUTHORIZED_ACCESS;

  /**
   * Constructs a <code>InvalidSessionException</code> with the specified message.
   *
   */
  public InvalidSessionException() {
    super(ERROR_CODE.errorMessage());
  }

  public ErrorCode errorCode() {
    return ERROR_CODE;
  }

}
