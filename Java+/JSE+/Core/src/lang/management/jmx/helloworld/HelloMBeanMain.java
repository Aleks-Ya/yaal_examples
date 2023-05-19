package lang.management.jmx.helloworld;

import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * Example from JSE Tutorial
 * <a href="http://docs.oracle.com/javase/tutorial/jmx/mbeans/standard.html">JSE Tutorial</a>.<br/>
 * JMX client: jconsole
 */
public class HelloMBeanMain {
    public static void main(String[] args) throws Exception {
        var mbs = ManagementFactory.getPlatformMBeanServer();
        var name = new ObjectName("com.example:type=Hello");
        var mbean = new Hello();
        mbs.registerMBean(mbean, name);

        System.out.println("Waiting forever...");
        Thread.sleep(Long.MAX_VALUE);
    }
}