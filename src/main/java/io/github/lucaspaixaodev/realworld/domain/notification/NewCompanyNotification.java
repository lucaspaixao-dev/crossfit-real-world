package io.github.lucaspaixaodev.realworld.domain.notification;

public class NewCompanyNotification extends Notification {

    private final String companyId;

    public NewCompanyNotification(String companyId, String companyName) {
        super(NotificationChannel.EMAIL, NotificationType.NEW_COMPANY);
        this.companyId = companyId;
    }

    @Override
    public Message getMessage() {
        return new Message(companyId);
    }
}
