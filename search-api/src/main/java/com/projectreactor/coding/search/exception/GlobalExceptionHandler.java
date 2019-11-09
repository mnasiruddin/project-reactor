package com.projectreactor.coding.search.exception;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public ResponseEntity<ApiError> handleIOException(UsernameAlreadyExistsException exception) {
    return sendErrorWithErrorLogLevel(new ApiError(HttpStatus.BAD_REQUEST, exception.errorCode(), exception.getMessage()), "Username already exists");
  }

  @ExceptionHandler(AccountCreationException.class)
  public ResponseEntity<ApiError> handleIOException(AccountCreationException exception) {
    return sendErrorWithErrorLogLevel(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception.errorCode(), exception.getMessage()), "unable to create new account at this time");
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ApiError> handleIOException(UsernameNotFoundException exception) {
    return sendErrorWithErrorLogLevel(new ApiError(HttpStatus.NOT_FOUND, exception.errorCode(), exception.getMessage()), "Username not found");
  }

  @ExceptionHandler(UnAuthorizeException.class)
  public ResponseEntity<ApiError> handleIOException(UnAuthorizeException exception) {
    return sendErrorWithErrorLogLevel(new ApiError(HttpStatus.UNAUTHORIZED, exception.errorCode(), exception.getMessage()), "credentails combination was UnAuthorized ");
  }

  @ExceptionHandler(NomineesNotFoundException.class)
  public ResponseEntity<ApiError> handleIOException(NomineesNotFoundException exception) {
    return sendErrorWithInfoLogLevel(new ApiError(HttpStatus.NO_CONTENT, exception.errorCode(), exception.getMessage()), "No Nominees found for the request");
  }

  @ExceptionHandler({ MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class})
  public ResponseEntity<ApiError> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex, WebRequest request) {
    final var errorMessage = null != ex.getRequiredType() ? ex.getRequiredType().getName() : "";
    String error = ex.getName() + " should be of type " + errorMessage;

    return sendErrorWithErrorLogLevel(new ApiError(HttpStatus.BAD_REQUEST, ErrorCode.ERROR, error), "run time error occured");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ApiError> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    List<String> errors = new ArrayList<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }

    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ErrorCode.ERROR, ex.getLocalizedMessage(), errors);
    return sendErrorWithErrorLogLevel(apiError, "Invalid method args");
  }

  private ResponseEntity<ApiError> sendErrorWithErrorLogLevel(ApiError apiError, String logMessage) {
    LOGGER.error(logMessage);
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  private ResponseEntity<ApiError> sendErrorWithInfoLogLevel(ApiError apiError, String logMessage) {
    LOGGER.info(logMessage);
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
