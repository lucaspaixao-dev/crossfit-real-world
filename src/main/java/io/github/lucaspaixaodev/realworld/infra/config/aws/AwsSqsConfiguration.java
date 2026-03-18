package io.github.lucaspaixaodev.realworld.infra.config.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Configuration
public class AwsSqsConfiguration {

    @Bean
    public SqsClient sqsClient(@Value("${aws.region}") String region,
            @Value("${aws.sqs.endpoint:#{null}}") String endpoint) {
        var builder = SqsClient.builder().region(Region.of(region));

        if (endpoint != null && !endpoint.isBlank()) {
            builder.endpointOverride(URI.create(endpoint)).credentialsProvider(
                    StaticCredentialsProvider.create(AwsBasicCredentials.create("localstack", "localstack")));
        }

        return builder.build();
    }
}
