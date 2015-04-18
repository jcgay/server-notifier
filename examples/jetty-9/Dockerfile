FROM jetty:9

RUN sed -i 's#<Configure id="Server" class="org.eclipse.jetty.server.Server">#<Configure id="Server" class="org.eclipse.jetty.server.Server">\n<Call name="addLifeCycleListener">\n<Arg>\n<New class="fr.jcgay.servernotifier.jetty.JettyNotifierListener" />\n</Arg>\n</Call>#g' $JETTY_HOME/etc/jetty.xml

RUN apt-get update && apt-get install -y wget maven default-jdk

# Build notifier and example webapp
ENV ROOT_SRC /tmp/sources
ENV WEBAPP $ROOT_SRC/examples/example-webapp

ADD sources $ROOT_SRC

RUN mvn clean package -DskipTests -f $ROOT_SRC/pom.xml -Denv.TRAVIS \
    && mvn clean package -DskipTests -f $WEBAPP/pom.xml -Denv.TRAVIS \
    && cp $WEBAPP/target/example-webapp*.war $JETTY_BASE/webapps \
    && cp $ROOT_SRC/jetty-notifier/target/jetty-notifier*.jar $JETTY_BASE/lib/ext \
    && wget -P $JETTY_BASE/lib/ext/ http://search.maven.org/remotecontent?filepath=org/slf4j/slf4j-simple/1.7.12/slf4j-simple-1.7.12.jar

ADD .send-notification /root/.send-notification
