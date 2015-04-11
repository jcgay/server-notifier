FROM jetty:9

RUN sed -i 's#<Configure id="Server" class="org.eclipse.jetty.server.Server">#<Configure id="Server" class="org.eclipse.jetty.server.Server">\n<Call name="addLifeCycleListener">\n<Arg>\n<New class="fr.jcgay.servernotifier.jetty.JettyNotifierListener" />\n</Arg>\n</Call>#g' $JETTY_HOME/etc/jetty.xml

ADD example-webapp.war $JETTY_BASE/webapps/example-webapp.war
ADD jetty-notifier.jar $JETTY_BASE/lib/ext/jetty-notifier.jar
ADD .send-notification /root/.send-notification
