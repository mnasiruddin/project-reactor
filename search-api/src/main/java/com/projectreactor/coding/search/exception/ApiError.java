package com.projectreactor.coding.search.exception;

import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {

  private HttpStatus status;
  private ErrorCode errorCode;
  private String errorMessage;
  private List<String> errors;

  public ApiError(HttpStatus status, ErrorCode errorCode, List<String> errors) {
    this(status, errorCode, null, errors);
  }

  public ApiError(HttpStatus status, ErrorCode errorCode, String error) {
    this(status, errorCode, null, Arrays.asList(error));
  }

  public ApiError(HttpStatus status, ErrorCode errorCode, String errorMessage, List<String> errors) {
    super();
    this.status = status;
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.errors = errors;
  }
}
