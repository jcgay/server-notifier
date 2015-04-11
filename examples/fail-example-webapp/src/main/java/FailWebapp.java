import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class FailWebapp implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        throw new RuntimeException("fail");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
