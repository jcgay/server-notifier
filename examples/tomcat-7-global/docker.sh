#!/bin/bash

if [ "$1" == "build" ]; then
    version=`xmlstarlet sel -N ns="http://maven.apache.org/POM/4.0.0" -t -c "/ns:project/ns:version/text()" -n ../../pom.xml`
    webapp='../example-webapp'
    rootfolder=`dirname $0`
    rm -rf $rootfolder/.send-notification $rootfolder/sources
    cp ../.send-notification . &&
    sed -i '' 's/#apikey#/'$2'/g;s/#device#/'$3'/g' .send-notification &&
    rsync -av --exclude='/.git' --filter="dir-merge,- ../../.gitignore" ../.. $rootfolder/sources &&
    docker build -t="jcgay/example-webapp-tomcat-7-global" .
elif [ "$1" == "run" ]; then
    docker run -d jcgay/example-webapp-tomcat-7-global
else
    echo "Try again: <$0 build> OR <$0 run>"
fi

