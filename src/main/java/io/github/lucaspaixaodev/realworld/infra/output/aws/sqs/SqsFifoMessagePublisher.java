package io.github.lucaspaixaodev.realworld.infra.output.aws.sqs;

import io.github.lucaspaixaodev.realworld.domain.notification.Message;
import io.github.lucaspaixaodev.realworld.domain.notification.Notification;
import io.github.lucaspaixaodev.realworld.domain.notification.publisher.NotificationPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.UUID;

@Component
public class SqsFifoMessagePublisher implements NotificationPublisher {

    private static final Logger log = LoggerFactory.getLogger(SqsFifoMessagePublisher.class);

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    public SqsFifoMessagePublisher(SqsClient sqsClient, ObjectMapper objectMapper) {
        this.sqsClient = sqsClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publish(Notification notification, String queueName, String messageGroupId) {
        log.info("Publishing message to SQS FIFO queue. queue={}, messageGroupId={}", queueName, messageGroupId);

        Message payload = notification.getMessage();
        String messageBody = objectMapper.writeValueAsString(payload);
        String queueUrl = resolveQueueUrl(queueName);

        Map<String, MessageAttributeValue> attributes = Map.of("type", MessageAttributeValue.builder()
                .dataType("String")
                .stringValue(notification.getType().name())
                .build());

        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .messageGroupId(messageGroupId)
                .messageDeduplicationId(UUID.randomUUID().toString())
                .messageAttributes(attributes)
                .build());

        log.info("Message published successfully to SQS FIFO queue. queue={}, messageGroupId={}", queueName,
                messageGroupId);
    }

    private String resolveQueueUrl(String queueName) {
        return sqsClient.getQueueUrl(GetQueueUrlRequest.builder()
                .queueName(queueName)
                .build()).queueUrl();
    }
}
