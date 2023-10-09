package org.folio.circulation.item.exception;

public class ResourceAlreadyExistException extends RuntimeException {

  public ResourceAlreadyExistException(String errorMsg) {
    super(errorMsg);
  }
}
