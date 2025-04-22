package com.example.demo.exception;

public class DoesNotExistException extends RuntimeException {
    public DoesNotExistException(String message) {
      super(message);
    }
}
