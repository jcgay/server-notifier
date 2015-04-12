#Server Notifier

A collection of desktop notifications for your webapp containers (Tomcat, Jetty).

## Configuration

Notifications are based on [send-notification](https://github.com/jcgay/send-notification), you should visit its [wiki](https://github.com/jcgay/send-notification/wiki) to find which notifier you want to use and how to configure it.  
By default it will try to use `Growl` for Windows, OSX and `notify-send` for `Linux`.

## Tomcat 7/8

### Installation

 - Get [tomcat-notifier](tomcat-notifier-shaded) and copy it in `$CATALINA_HOME/lib`
 - Configure a new [listener](https://tomcat.apache.org/tomcat-7.0-doc/config/listeners.html) in `$CATALINA_HOME/conf/Catalina/[host]/[application].xml` (ie `context.xml`) or in `$CATALINA_HOME/conf/server.xml` depending if you want a notification for just a webapp or for the server globally  

    ```
    <Listener className="fr.jcgay.servernotifier.tomcat.TomcatNotifierListener" />
    ```

You can find example of `context.xml` or `server.xml` in [example-webapp]().

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
                <version>0.1-SNAPSHOT</version>
            </dependency>
        </dependencies>
    </plugin>
```
with `context.xml` minimum configuration:

```
<?xml version='1.0' encoding='utf-8'?>
<Context>
	<Listener className="fr.jcgay.servernotifier.tomcat.TomcatNotifierListener" />
</Context>
```