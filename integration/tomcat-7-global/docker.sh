#!/bin/bash

if [ "$1" == "build" ]; then
    version=`xmlstarlet sel -N ns="http://maven.apache.org/POM/4.0.0" -t -c "/ns:project/ns:version/text()" -n ../../pom.xml` &&
    mvn clean package -f ../../pom.xml &&
    cp ../../tomcat-notifier/target/tomcat-notifier-$version-shaded.jar tomcat-notifier.jar
    mvn clean package -f ../../example-webapp/pom.xml &&
    cp ../../example-webapp/target/example-webapp.war . &&
    cp ../.send-notification . &&
    sed -i '' 's/#apikey#/'$2'/g;s/#device#/'$3'/g' .send-notification &&
    docker build -t="jcgay/example-webapp-tomcat-7-global" .
elif [ "$1" == "run" ]; then
    docker run -d -p 8080:8080 jcgay/example-webapp-tomcat-7-global
else
    echo "Try again: <$0 build> OR <$0 run>"
fi
