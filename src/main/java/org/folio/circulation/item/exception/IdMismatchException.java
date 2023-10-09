package org.folio.circulation.item.exception;

public class IdMismatchException extends RuntimeException {

    public IdMismatchException(String errorMsg) {
        super(errorMsg);
    }
}
