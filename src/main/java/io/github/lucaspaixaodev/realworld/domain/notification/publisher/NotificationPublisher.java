package io.github.lucaspaixaodev.realworld.domain.notification.publisher;

import io.github.lucaspaixaodev.realworld.domain.notification.Notification;

public interface NotificationPublisher {

    void publish(Notification notification, String queueName, String messageGroupId);
}
