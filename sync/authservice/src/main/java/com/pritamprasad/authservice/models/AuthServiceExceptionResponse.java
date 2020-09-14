package com.pritamprasad.authservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties
@Builder
public class AuthServiceExceptionResponse {

    private String exceptionId;

    private String timestamp;

    private String message;

    private Object data;
}
