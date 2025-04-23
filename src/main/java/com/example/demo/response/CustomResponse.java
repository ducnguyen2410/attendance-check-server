package com.example.demo.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomResponse<T> {
    private int statusCode;
    private T data;
    private LocalDateTime timestamp;

    public CustomResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public CustomResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}
