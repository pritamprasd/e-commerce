package com.pritamprasad.authservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionConfig {
    private String id;
    private String description;
    private String user_message;
}