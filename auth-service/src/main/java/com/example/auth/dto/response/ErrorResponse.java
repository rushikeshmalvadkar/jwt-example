package com.example.auth.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;


@Getter
@Setter
public final class ErrorResponse {
    private List<String> errorMessages;
    private HttpStatus status;

    public int getCode() {
        return status.value();
    }

}
