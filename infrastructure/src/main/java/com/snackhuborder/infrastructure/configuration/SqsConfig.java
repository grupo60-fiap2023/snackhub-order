package com.snackhuborder.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Configuration
public class SqsConfig {

    @Autowired
    private Environment environment;

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        String urlEndPoint = environment.getProperty("cloud.sqs.endpoint-listener");
        String awsRegion = environment.getProperty("cloud.sqs.aws-region");
        return SqsAsyncClient.builder()
                .endpointOverride(URI.create(urlEndPoint))
                .region(Region.of(awsRegion))
                .build();
    }
}
