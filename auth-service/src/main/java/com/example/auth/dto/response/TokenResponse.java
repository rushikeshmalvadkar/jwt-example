package com.example.auth.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TokenResponse {

    private boolean validated;

    private String email;

    private List<String> authorities;

}
