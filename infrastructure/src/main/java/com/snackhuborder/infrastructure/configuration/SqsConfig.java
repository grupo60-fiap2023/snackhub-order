package com.snackhuborder.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Configuration
public class SqsConfig {

    @Autowired
    private Environment environment;

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        String awsRegion = environment.getProperty("cloud.sqs.aws-region");
        String accessKeyId = environment.getProperty("cloud.sqs.accessKeyId");
        String secretAccessKey = environment.getProperty("cloud.sqs.secretAccessKey");

        return SqsAsyncClient.builder()
                .credentialsProvider(() -> AwsBasicCredentials.create(accessKeyId, secretAccessKey))
                .region(Region.of(awsRegion))
                .build();
    }
}
