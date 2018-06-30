package com.honsul.stampist.core;

public class StampistException extends RuntimeException {
  private static final long serialVersionUID = 3363232716110628168L;

  public StampistException() {
    super();
  }

  public StampistException(String message) {
    super(message);
  }

  public StampistException(String message, Throwable cause) {
    super(message, cause);
  }
}
