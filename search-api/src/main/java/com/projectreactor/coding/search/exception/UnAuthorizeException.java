package com.projectreactor.coding.search.exception;

public class UnAuthorizeException extends RuntimeException {

  private static final ErrorCode ERROR_CODE = ErrorCode.UN_AUTHORIZED_ACCESS;

  /**
   * Constructs a <code>UnAuthorizeException</code> with the specified message.
   *
   */
  public UnAuthorizeException() {
    super(ERROR_CODE.errorMessage());
  }

  public ErrorCode errorCode() {
    return ERROR_CODE;
  }

}
