#Server Notifier

A collection of desktop notifications for your webapp containers (Tomcat, Jetty) to indicate when it has started or stopped.

## Configuration

Notifications are based on [send-notification](https://github.com/jcgay/send-notification), you should visit its [wiki](https://github.com/jcgay/send-notification/wiki) to find which notifier you want to use and how to configure it.  
By default it will try to use `Growl` for Windows, OSX and `notify-send` for `Linux`.

## Tomcat 7/8

### Installation

 - Get [tomcat-notifier](http://search.maven.org/remotecontent?filepath=fr/jcgay/server-notifier/tomcat-notifier/0.1/tomcat-notifier-0.1-shaded.jar) and copy it in `$CATALINA_HOME/lib`
 - Configure a new [listener](https://tomcat.apache.org/tomcat-7.0-doc/config/listeners.html) in `$CATALINA_HOME/conf/Catalina/[host]/[application].xml` (ie `context.xml`) or in `$CATALINA_HOME/conf/server.xml` depending if you want a notification for just a webapp or for the server globally  

    ```
    <Listener className="fr.jcgay.servernotifier.tomcat.TomcatNotifierListener" />
    ```
 - Choose a [`SLF4J`](http://www.slf4j.org/manual.html#swapping) binding to configure logging and copy it in `$CATALINA_HOME/lib` (you can take [slf4j-simple](http://search.maven.org/remotecontent?filepath=org/slf4j/slf4j-simple/1.7.12/slf4j-simple-1.7.12.jar))
 
You can find example of `context.xml` or `server.xml` in [example-webapp](examples/example-webapp/src/tomcat/).

### Usage with Maven plugin

Add plugin in `pom.xml`:

```
    <plugin>
        <groupId>org.apache.tomcat.maven</groupId>
        <artifactId>tomcat7-maven-plugin</artifactId>
        <version>2.2</version>
        <configuration>
            <contextFile>src/tomcat/context.xml</contextFile>
        </configuration>
        <dependencies>
            <dependency>
                <groupId>fr.jcgay.server-notifier</groupId>
                <artifactId>tomcat-notifier</artifactId>
                <version>0.1</version>
            </dependency>
            <dependency>
                <groupId>org.slf4j</groupId>
                <artifactId>slf4j-simple</artifactId>
                <version>1.7.12</version>
            </dependency>
        </dependencies>
    </plugin>
```
with `context.xml` minimal configuration:

```
<?xml version='1.0' encoding='utf-8'?>
<Context>
	<Listener className="fr.jcgay.servernotifier.tomcat.TomcatNotifierListener" />
</Context>
```

## Jetty 7/8/9

### Installation

- Get [jetty-notifier](http://search.maven.org/remotecontent?filepath=fr/jcgay/server-notifier/jetty-notifier/0.1/jetty-notifier-0.1-shaded.jar) and copty it in `$JETTY_HOME/lib/ext`
- Configure a new listener in `$JETTY_HOME/etc/jetty.xml`

	```
	<Configure id="Server" class="org.eclipse.jetty.server.Server">
        <Call name="addLifeCycleListener">
            <Arg>
                <New class="fr.jcgay.servernotifier.jetty.JettyNotifierListener"/>
            </Arg>
        </Call>
    </Configure>
	```
 - Choose a [`SLF4J`](http://www.slf4j.org/manual.html#swapping) binding to configure logging and copy it in `$CATALINA_HOME/lib` (you can take [slf4j-simple](http://search.maven.org/remotecontent?filepath=org/slf4j/slf4j-simple/1.7.12/slf4j-simple-1.7.12.jar))
 
You can find example of `jetty.xml` in [example-webapp](examples/example-webapp/src/jetty/)

### Usage with Maven plugin

Add plugin in `pom.xml`:

```
    <plugin>
        <groupId>org.mortbay.jetty</groupId>
        <artifactId>jetty-maven-plugin</artifactId>
        <version>7.6.16.v20140903</version>
        <configuration>
            <jettyXml>src/jetty/jetty.xml</jettyXml>
        </configuration>
        <dependencies>
            <dependency>
                <groupId>fr.jcgay.server-notifier</groupId>
                <artifactId>jetty-notifier</artifactId>
                <version>0.1</version>
            </dependency>
            <dependency>
                <groupId>org.slf4j</groupId>
                <artifactId>slf4j-simple</artifactId>
                <version>1.7.12</version>
            </dependency>
        </dependencies>
    </plugin>
```
with `jetty.xml` minimal configuration:

```
    <?xml version="1.0"?>
    <!DOCTYPE Configure PUBLIC "-//Jetty//Configure//EN" "http://www.eclipse.org/jetty/configure.dtd">
    <Configure id="Server" class="org.eclipse.jetty.server.Server">
        <Call name="addLifeCycleListener">
            <Arg>
                <New class="fr.jcgay.servernotifier.jetty.JettyNotifierListener"/>
            </Arg>
        </Call>
    </Configure>
```

# Build status
[![Build Status](https://travis-ci.org/jcgay/server-notifier.svg?branch=master)](https://travis-ci.org/jcgay/server-notifier)
[![Coverage Status](https://coveralls.io/repos/jcgay/server-notifier/badge.svg?branch=master)](https://coveralls.io/r/jcgay/server-notifier?branch=master)
