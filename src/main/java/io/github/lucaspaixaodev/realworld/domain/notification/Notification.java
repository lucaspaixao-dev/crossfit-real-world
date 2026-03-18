package io.github.lucaspaixaodev.realworld.domain.notification;

public abstract class Notification {

    private final NotificationChannel channel;
    private final NotificationType type;

    protected Notification(NotificationChannel channel, NotificationType type) {
        this.channel = channel;
        this.type = type;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public NotificationType getType() {
        return type;
    }

    public abstract Message getMessage();
}
