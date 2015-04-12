package fr.jcgay.servernotifier.tomcat;

import fr.jcgay.notification.Application;
import fr.jcgay.notification.Icon;
import fr.jcgay.notification.Notification;
import fr.jcgay.notification.Notifier;
import fr.jcgay.notification.SendNotification;
import fr.jcgay.notification.SendNotificationException;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.core.StandardContext;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import java.util.concurrent.TimeUnit;

import static org.apache.catalina.Lifecycle.AFTER_START_EVENT;
import static org.apache.catalina.Lifecycle.AFTER_STOP_EVENT;
import static org.apache.catalina.Lifecycle.BEFORE_STOP_EVENT;
import static org.apache.catalina.LifecycleState.FAILED;
import static org.apache.catalina.LifecycleState.STARTED;
import static org.apache.catalina.LifecycleState.STOPPED;

public class TomcatNotifierListener implements LifecycleListener{

    private static final Log LOGGER = LogFactory.getLog(TomcatNotifierListener.class);

    private static final String TOMCAT = "Tomcat";

    private final Icon successIcon = Icons.success();
    private final Icon failIcon = Icons.failure();
    private final Icon appIcon = Icons.tomcat();
    private final Notifier notifier;

    private boolean skipNotifications;
    private boolean hasFailed;

    public TomcatNotifierListener() {
        Application application = Application.builder("application/x-vnd-apache.tomcat", TOMCAT, appIcon)
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

    TomcatNotifierListener(Notifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        if (skipNotifications) {
            return;
        }

        if (hasType(event, BEFORE_STOP_EVENT) && hasState(event, FAILED)) {
            hasFailed = true;

            Notification notification =
                    Notification.builder(TOMCAT, getName(event) + " has failed to start !", failIcon)
                            .build();
            send(notification);
        }

        if (hasType(event, AFTER_START_EVENT) && hasState(event, STARTED)) {
            Notification notification =
                    Notification.builder(TOMCAT, getName(event) + " has started !", successIcon)
                            .build();
            send(notification);
        }

        if (hasType(event, AFTER_STOP_EVENT) && hasState(event, STOPPED) && !hasFailed) {
            Notification notification =
                    Notification.builder(TOMCAT, getName(event) + " has stopped !", appIcon)
                            .build();
            send(notification);
        }

        if (hasType(event, AFTER_STOP_EVENT)) {
            notifier.close();
        }
    }

    private void send(Notification notification) {
        try {
            notifier.send(notification);
        } catch (SendNotificationException e) {
            LOGGER.warn("Cannot send notification...", e);
        }
    }

    private static boolean hasState(LifecycleEvent event, LifecycleState state) {
        return event.getLifecycle().getState() == state;
    }

    private static boolean hasType(LifecycleEvent event, String type) {
        return type.equals(event.getType());
    }


    private static String getName(LifecycleEvent event) {
        if (event.getSource() instanceof StandardContext) {
            return ((StandardContext) event.getSource()).getName();
        }
        return TOMCAT;
    }
}
