package org.folio.circulation.item.controller;

import org.springframework.web.client.HttpServerErrorException;
import lombok.extern.log4j.Log4j2;
import org.folio.circulation.item.exception.IdMismatchException;
import org.folio.circulation.item.domain.dto.Errors;
import org.folio.circulation.item.exception.ResourceAlreadyExistException;
import org.folio.spring.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.folio.circulation.item.utils.ErrorHelper.ErrorCode.BAD_GATEWAY;
import static org.folio.circulation.item.utils.ErrorHelper.ErrorCode.DUPLICATE_ERROR;
import static org.folio.circulation.item.utils.ErrorHelper.ErrorCode.INTERNAL_SERVER_ERROR;
import static org.folio.circulation.item.utils.ErrorHelper.ErrorCode.NOT_FOUND_ERROR;
import static org.folio.circulation.item.utils.ErrorHelper.ErrorCode.VALIDATION_ERROR;
import static org.folio.circulation.item.utils.ErrorHelper.createExternalError;
import static org.folio.circulation.item.utils.ErrorHelper.createInternalError;

@RestControllerAdvice
@Log4j2
public class ExceptionHandlingController {

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public Errors handleGlobalException(Exception ex) {
    logExceptionMessage(ex);
    return createExternalError(ex.getMessage(), INTERNAL_SERVER_ERROR);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IdMismatchException.class)
  public Errors handleBadRequestIdMismatchException(IdMismatchException ex) {
    logExceptionMessage(ex);
    return createExternalError(ex.getMessage(), VALIDATION_ERROR);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public Errors handleNotFoundException(NotFoundException ex) {
    logExceptionMessage(ex);
    return createExternalError(ex.getMessage(), NOT_FOUND_ERROR);
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(ResourceAlreadyExistException.class)
  public Errors handleAlreadyExistException(ResourceAlreadyExistException ex) {
    logExceptionMessage(ex);
    return createExternalError(ex.getMessage(), DUPLICATE_ERROR);
  }

  @ResponseStatus(HttpStatus.BAD_GATEWAY)
  @ExceptionHandler(HttpServerErrorException.BadGateway.class)
  public Errors handleBadGatewayException(HttpServerErrorException.BadGateway ex) {
    logExceptionMessage(ex);
    return createInternalError(ex.getMessage(), BAD_GATEWAY);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({
    MissingServletRequestParameterException.class,
    MethodArgumentTypeMismatchException.class,
    HttpMessageNotReadableException.class,
    IllegalArgumentException.class
  })
  public Errors handleValidationErrors(Exception ex) {
    logExceptionMessage(ex);
    return createExternalError(ex.getMessage(), VALIDATION_ERROR);
  }

  private void logExceptionMessage(Exception ex) {
    log.warn("Exception occurred ", ex);
  }

}
