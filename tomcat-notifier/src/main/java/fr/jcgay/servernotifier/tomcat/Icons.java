package fr.jcgay.servernotifier.tomcat;

import fr.jcgay.notification.Icon;

class Icons {

    private Icons() {
    }

    public static Icon tomcat() {
        return createIcon("tomcat.png", "tomcat");
    }

    public static Icon success() {
        return createIcon("tomcat.success.png", "tomcat-success");
    }

    public static Icon failure() {
        return createIcon("tomcat.fail.png", "tomcat-fail");
    }

    private static Icon createIcon(String resource, String id) {
        return Icon.create(Thread.currentThread().getContextClassLoader().getResource(resource), id);
    }
}
