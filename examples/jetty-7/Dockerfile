FROM java:7

RUN apt-get update && apt-get install -y wget maven default-jdk

# Install Jetty
ENV JETTY_VERSION 7.6.15.v20140411
WORKDIR /tmp
RUN wget http://central.maven.org/maven2/org/mortbay/jetty/dist/jetty-deb/$JETTY_VERSION/jetty-deb-$JETTY_VERSION.deb && dpkg -i jetty-deb-$JETTY_VERSION.deb

ENV JETTY_HOME /opt/jetty

# Change configuration to use notifier listener
RUN sed -i 's#<Configure id="Server" class="org.eclipse.jetty.server.Server">#<Configure id="Server" class="org.eclipse.jetty.server.Server">\n<Call name="addLifeCycleListener">\n<Arg>\n<New class="fr.jcgay.servernotifier.jetty.JettyNotifierListener" />\n</Arg>\n</Call>#g' $JETTY_HOME/etc/jetty.xml

# Build notifier and example webapp
ENV ROOT_SRC /tmp/sources
ENV WEBAPP $ROOT_SRC/examples/example-webapp

ADD sources $ROOT_SRC

RUN mvn clean package -DskipTests -f $ROOT_SRC/pom.xml -Denv.TRAVIS \
    && mvn clean package -DskipTests -f $WEBAPP/pom.xml -Denv.TRAVIS \
    && cp $WEBAPP/target/example-webapp*.war $JETTY_HOME/webapps/ \
    && cp $ROOT_SRC/jetty-notifier/target/jetty-notifier*.jar $JETTY_HOME/lib/ext/ \
    && wget -P $JETTY_HOME/lib/ext/ http://search.maven.org/remotecontent?filepath=org/slf4j/slf4j-simple/1.7.12/slf4j-simple-1.7.12.jar

ADD .send-notification /root/.send-notification

# Clean up APT when done.
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

CMD ["/etc/init.d/jetty", "run"]
