FROM tomcat:7

ADD example-webapp.war $CATALINA_HOME/webapps/example-webapp.war
ADD context.xml $CATALINA_HOME/conf/Catalina/localhost/example-webapp.xml
ADD tomcat-7-notifier.jar $CATALINA_HOME/lib/tomcat-7-notifier.jar
ADD .send-notification /root/.send-notification
