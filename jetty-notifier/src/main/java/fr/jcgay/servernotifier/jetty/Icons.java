package fr.jcgay.servernotifier.jetty;

import fr.jcgay.notification.Icon;

class Icons {

    private Icons() {
    }

    public static Icon jetty() {
        return createIcon("jetty.png", "jetty");
    }

    public static Icon success() {
        return jetty();
    }

    public static Icon failure() {
        return jetty();
    }

    private static Icon createIcon(String resource, String id) {
        return Icon.create(Thread.currentThread().getContextClassLoader().getResource(resource), id);
    }
}
