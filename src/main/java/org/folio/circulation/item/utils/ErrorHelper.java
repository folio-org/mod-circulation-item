package org.folio.circulation.item.utils;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.folio.circulation.item.domain.dto.Error;
import org.folio.circulation.item.domain.dto.Errors;

import java.util.List;

@UtilityClass
public class ErrorHelper {

  public static Error createError(String message, ErrorType type, ErrorCode errorCode) {
    return Error.builder()
      .message(message)
      .type(type.getTypeCode())
      .code(errorCode == null ? null : errorCode.name())
      .build();
  }

  public static Errors createErrors(Error error) {
    return Errors.builder()
      .errors(List.of(error))
      .build();
  }


  public static Errors createInternalError(String message, ErrorCode errorCode) {
    return createErrors(createError(message, ErrorType.INTERNAL, errorCode));
  }

  public static Errors createExternalError(String message, ErrorCode errorCode) {
    return createErrors(createError(message, ErrorType.EXTERNAL, errorCode));
  }

  @Getter
  public enum ErrorType {
    EXTERNAL("-1"), // bad request or client error
    INTERNAL("-2"); // bad gateway or internal error (db error)

    private final String typeCode;

    ErrorType(String typeCode) {
      this.typeCode = typeCode;
    }

  }

  public enum ErrorCode {
    VALIDATION_ERROR,
    NOT_FOUND_ERROR,
    DUPLICATE_ERROR,
    BAD_GATEWAY,
    INTERNAL_SERVER_ERROR
  }

}
