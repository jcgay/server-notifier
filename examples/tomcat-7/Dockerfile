FROM tomcat:7

ADD example-webapp.war $CATALINA_HOME/webapps/example-webapp.war
ADD context.xml $CATALINA_HOME/conf/Catalina/localhost/example-webapp.xml
ADD tomcat-notifier.jar $CATALINA_HOME/lib/tomcat-notifier.jar
ADD .send-notification /root/.send-notification
