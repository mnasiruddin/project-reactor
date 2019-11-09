package com.projectreactor.coding.search.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

  private static final ErrorCode ERROR_CODE = ErrorCode.USER_NAME_ALREADY_EXISTS;

  /**
   * Constructs a <code>UsernameAlreadyExistsException</code> with the specified message.
   *
   */
  public UsernameAlreadyExistsException() {
    super(ERROR_CODE.errorMessage());
  }

  public ErrorCode errorCode() {
    return ERROR_CODE;
  }
}
