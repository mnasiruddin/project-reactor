package com.projectreactor.coding.search.exception;

public class NomineesNotFoundException extends RuntimeException {

  private static final ErrorCode ERROR_CODE = ErrorCode.NOMINEES_NOT_FOUND;

  /**
   * Constructs a <code>NomineesNotFoundException</code> with the specified message.
   *
   */
  public NomineesNotFoundException() {
    super(ERROR_CODE.errorMessage());
  }

  public ErrorCode errorCode() {
    return ERROR_CODE;
  }
}
