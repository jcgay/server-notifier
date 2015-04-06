FROM tomcat:7

RUN sed -i 's#<Listener className="org.apache.catalina.core.ThreadLocalLeakPreventionListener" />#<Listener className="org.apache.catalina.core.ThreadLocalLeakPreventionListener" />\n<Listener className="fr.jcgay.servernotifier.Tomcat7NotifierListener" />#g' $CATALINA_HOME/conf/server.xml

ADD example-webapp.war $CATALINA_HOME/webapps/example-webapp.war
ADD tomcat-7-notifier.jar $CATALINA_HOME/lib/tomcat-7-notifier.jar
ADD .send-notification /root/.send-notification
