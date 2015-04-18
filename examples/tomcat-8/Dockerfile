FROM tomcat:8

RUN apt-get update && apt-get install -y wget maven default-jdk

# Build notifier and example webapp
ENV ROOT_SRC /tmp/sources
ENV WEBAPP $ROOT_SRC/examples/example-webapp

ADD sources $ROOT_SRC

RUN mvn clean package -DskipTests -f $ROOT_SRC/pom.xml -Denv.TRAVIS \
    && mvn clean package -DskipTests -f $WEBAPP/pom.xml -Denv.TRAVIS \
    && cp $WEBAPP/target/example-webapp*.war $CATALINA_HOME/webapps \
    && mkdir -p $CATALINA_HOME/conf/Catalina/localhost \
    && cp $WEBAPP/src/tomcat/context.xml $CATALINA_HOME/conf/Catalina/localhost/example-webapp.xml \
    && cp $ROOT_SRC/tomcat-notifier/target/tomcat-notifier*.jar $CATALINA_HOME/lib \
    && wget -P $CATALINA_HOME/lib http://search.maven.org/remotecontent?filepath=org/slf4j/slf4j-simple/1.7.12/slf4j-simple-1.7.12.jar

ADD .send-notification /root/.send-notification

# Clean up APT when done.
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*
