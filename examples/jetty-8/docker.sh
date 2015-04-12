#!/bin/bash

if [ "$1" == "build" ]; then
    version=`xmlstarlet sel -N ns="http://maven.apache.org/POM/4.0.0" -t -c "/ns:project/ns:version/text()" -n ../../pom.xml`
    webapp='../example-webapp'
    rm *.jar *.war .send-notification
    mvn clean package -f ../../pom.xml &&
    cp ../../jetty-notifier/target/jetty-notifier-$version-shaded.jar jetty-notifier.jar
    mvn clean package -f $webapp/pom.xml &&
    cp $webapp/target/example-webapp.war . &&
    cp ../.send-notification . &&
    sed -i '' 's/#apikey#/'$2'/g;s/#device#/'$3'/g' .send-notification &&
    docker build -t="jcgay/example-webapp-jetty-8" .
elif [ "$1" == "run" ]; then
    docker run -d -p 8080:8080 jcgay/example-webapp-jetty-8
else
    echo "Try again: <$0 build> OR <$0 run>"
fi
