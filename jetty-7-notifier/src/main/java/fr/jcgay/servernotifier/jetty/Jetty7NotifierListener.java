package fr.jcgay.servernotifier.jetty;

import fr.jcgay.notification.Application;
import fr.jcgay.notification.Notification;
import fr.jcgay.notification.Notifier;
import fr.jcgay.notification.SendNotification;
import fr.jcgay.notification.SendNotificationException;
import org.eclipse.jetty.util.component.LifeCycle;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class Jetty7NotifierListener implements LifeCycle.Listener {

    private static final Logger LOGGER = getLogger(Jetty7NotifierListener.class);

    private final Notifier notifier;

    private boolean skipNotifications;

    public Jetty7NotifierListener() {
        Application application = Application.builder("application/x-vnd-eclipse.jetty", "Jetty", Icons.jetty())
                .withTimeout(TimeUnit.SECONDS.toMillis(2))
                .build();

        notifier = new SendNotification()
                .setApplication(application)
                .chooseNotifier();

        try {
            notifier.init();
        } catch (SendNotificationException e) {
            LOGGER.warn("Fail to initialize notification system, skipping notifications...", e);
            skipNotifications = true;
        }
    }

    Jetty7NotifierListener(Notifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void lifeCycleStarting(LifeCycle lifeCycle) {}

    @Override
    public void lifeCycleStarted(LifeCycle lifeCycle) {
        if (skipNotifications) {
            return;
        }

        Notification notification =
                Notification.builder("Jetty", "Server has started !", Icons.jetty())
                        .build();
        send(notification);
    }

    @Override
    public void lifeCycleFailure(LifeCycle lifeCycle, Throwable throwable) {}

    @Override
    public void lifeCycleStopping(LifeCycle lifeCycle) {}

    @Override
    public void lifeCycleStopped(LifeCycle lifeCycle) {
        if (skipNotifications) {
            return;
        }

        Notification notification =
                Notification.builder("Jetty", "Server has stopped !", Icons.jetty())
                        .build();
        send(notification);

        notifier.close();
    }

    private void send(Notification notification) {
        try {
            notifier.send(notification);
        } catch (SendNotificationException e) {
            LOGGER.warn("Cannot send notification...", e);
        }
    }
}
