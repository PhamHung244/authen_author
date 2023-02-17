package com.example.authen.dtoResponse;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class AuthenResponse {

    private String status;

    private Object data;

    private String jwt;

    public AuthenResponse(String status, Object data, String jwt) {
        this.status = status;
        this.data = data;
        this.jwt = jwt;
    }
    public AuthenResponse(String status) {
        this.status = status;
    }
}
