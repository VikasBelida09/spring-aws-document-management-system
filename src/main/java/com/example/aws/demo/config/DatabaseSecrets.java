package com.example.aws.demo.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseSecrets {
    private String host;
    private String port;
    private String username;
    private String password;
    private String engine;
    private String dbInstanceIdentifier;
}
