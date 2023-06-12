package com.Valeram.travelagencymanagementbackend.exception;

public class DatabaseOperationFailedException extends RuntimeException {

  public DatabaseOperationFailedException(String message) {
    super(message);
  }

}
