package com.betteroscar.exception;

import com.betteroscar.database.Procedure;

public class DatabaseException extends Exception {

  public DatabaseException(String message, Throwable cause) {
    super(message, cause);
  }

  public DatabaseException(String message, Procedure procedure) {
    super(getProcMessage(message, procedure));
  }

  public DatabaseException(String message, Procedure procedure, Throwable cause) {
    super(getProcMessage(message, procedure), cause);
  }

  private static String getProcMessage(String message, Procedure procedure) {
    return message + " (Procedure: " + procedure + ")";
  }
}
