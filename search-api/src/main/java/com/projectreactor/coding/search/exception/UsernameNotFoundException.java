package com.projectreactor.coding.search.exception;

public class UsernameNotFoundException extends RuntimeException {

  private static final ErrorCode ERROR_CODE = ErrorCode.USER_NAME_NOT_FOUND;

  /**
   * Constructs a <code>UsernameNotFoundException</code> with the specified message.
   *
   */
  public UsernameNotFoundException() {
    super(ERROR_CODE.errorMessage());
  }

  public ErrorCode errorCode() {
    return ERROR_CODE;
  }
}
