package com.molla.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ResponseBody<T> {
    private T data;
    private String message;

    private StatusCode status;
    public ResponseBody(String message) {
        this.message = message;
    }

    public ResponseBody(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public ResponseBody(String message, StatusCode status) {
        this.message = message;
        this.status = status;
    }
}
