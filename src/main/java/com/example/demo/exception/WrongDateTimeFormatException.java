package com.example.demo.exception;

public class WrongDateTimeFormatException extends RuntimeException {
    public WrongDateTimeFormatException(String message) {
        super(message);
    }
}
