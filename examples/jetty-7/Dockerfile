FROM java:7

RUN apt-get update && apt-get install -y wget

ENV JETTY_VERSION 7.6.15.v20140411
WORKDIR /tmp
RUN wget http://central.maven.org/maven2/org/mortbay/jetty/dist/jetty-deb/$JETTY_VERSION/jetty-deb-$JETTY_VERSION.deb && dpkg -i jetty-deb-$JETTY_VERSION.deb

ENV JETTY_HOME /opt/jetty

RUN sed -i 's#<Configure id="Server" class="org.eclipse.jetty.server.Server">#<Configure id="Server" class="org.eclipse.jetty.server.Server">\n<Call name="addLifeCycleListener">\n<Arg>\n<New class="fr.jcgay.servernotifier.jetty.JettyNotifierListener" />\n</Arg>\n</Call>#g' $JETTY_HOME/etc/jetty.xml

ADD example-webapp.war $JETTY_HOME/webapps/example-webapp.war
ADD jetty-notifier.jar $JETTY_HOME/lib/ext/jetty-notifier.jar
ADD .send-notification /root/.send-notification

# Clean up APT when done.
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

CMD ["/etc/init.d/jetty", "run"]
