package lang.management.jmx.notifications;

import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * Example from <a href="http://docs.oracle.com/javase/tutorial/jmx/notifs/index.html">JSE Tutorial</a>.
 * <p>
 * JMX server: java NotificationsMain
 * JMX client: jconsole
 */
public class NotificationsMain {
    public static void main(String[] args) throws Exception {
        var mbs = ManagementFactory.getPlatformMBeanServer();
        var name = new ObjectName("com.example:type=Hello");
        var mbean = new Hello();
        mbs.registerMBean(mbean, name);

        System.out.println("Waiting forever...");
        Thread.sleep(Long.MAX_VALUE);
    }
}