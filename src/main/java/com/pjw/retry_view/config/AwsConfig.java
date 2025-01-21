package com.pjw.retry_view.config;

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.time.Duration;

@Configuration
public class AwsConfig {
    @Value("${spring.cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${spring.cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Value("${spring.cloud.aws.region.static}")
    private String region;

    @Bean
    public SnsClient snsClient(){
        return SnsClient.builder()
                .credentialsProvider(getAwsCredentialsProvider())
                .region(Region.of(region))
                .build();
    }

    @Bean
    public SqsAsyncClient sqsClient(){
        return SqsAsyncClient.builder()
                .credentialsProvider(getAwsCredentialsProvider())
                .region(Region.of(region))
                .build();
    }

    @Bean
    public SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(){
        return SqsMessageListenerContainerFactory.builder()
                .sqsAsyncClient(sqsClient())
                .configure(options-> options.maxConcurrentMessages(10)
                        .pollTimeout(Duration.ofSeconds(10)))
                .build();
    }

    @Bean
    public AwsCredentialsProvider getAwsCredentialsProvider(){
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
    }
}
