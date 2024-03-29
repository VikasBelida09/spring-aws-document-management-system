package com.example.aws.demo.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.regions.Region;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import javax.sql.DataSource;


@Configuration
public class DatabaseConfig {
    private DatabaseSecrets getSecret() throws JsonProcessingException {
        String secretName = "rds.mysql";
        Region region = Region.of("us-east-1");

        SecretsManagerClient secretsClient = SecretsManagerClient.builder()
                .region(region)
                .build();

        GetSecretValueRequest valueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();
        GetSecretValueResponse valueResponse = secretsClient.getSecretValue(valueRequest);

        secretsClient.close();
        System.out.println(valueResponse.secretString());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(valueResponse.secretString(), DatabaseSecrets.class);
    }

    @Bean
    public DataSource dataSource() throws Exception {
        DatabaseSecrets secrets = this.getSecret();
        System.out.println(secrets.toString());
        String jdbcUrl = "jdbc:mysql://" + secrets.getHost() + ":" + secrets.getPort() + "/" + secrets.getDbInstanceIdentifier();
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(secrets.getUsername());
        dataSource.setPassword(secrets.getPassword());
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        return dataSource;
    }

}
