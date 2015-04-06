FROM tomcat:7

RUN sed -i 's#<Listener className="org.apache.catalina.core.ThreadLocalLeakPreventionListener" />#<Listener className="org.apache.catalina.core.ThreadLocalLeakPreventionListener" />\n<Listener className="fr.jcgay.servernotifier.TomcatNotifierListener" />#g' $CATALINA_HOME/conf/server.xml

ADD example-webapp.war $CATALINA_HOME/webapps/example-webapp.war
ADD tomcat-notifier.jar $CATALINA_HOME/lib/tomcat-notifier.jar
ADD .send-notification /root/.send-notification
