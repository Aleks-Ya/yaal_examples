/*
 * Client.java - JMX client that interacts with the JMX agent. It gets
 * attributes and performs operations on the Hello MBean and the QueueSampler
 * MXBean example. It also listens for Hello MBean notifications.
 */

package lang.management.jmx.custom_jmx_client;

import javax.management.AttributeChangeNotification;
import javax.management.JMX;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Run: java com.example.Client
 */
public class Client {

    /**
     * Inner class that will handle the notifications.
     */
    public static class ClientListener implements NotificationListener {
        public void handleNotification(Notification notification, Object handback) {
            echo("\nReceived notification:");
            echo("\tClassName: " + notification.getClass().getName());
            echo("\tSource: " + notification.getSource());
            echo("\tType: " + notification.getType());
            echo("\tMessage: " + notification.getMessage());
            if (notification instanceof AttributeChangeNotification acn) {
                echo("\tAttributeName: " + acn.getAttributeName());
                echo("\tAttributeType: " + acn.getAttributeType());
                echo("\tNewValue: " + acn.getNewValue());
                echo("\tOldValue: " + acn.getOldValue());
            }
        }
    }

    /* For simplicity, we declare "throws Exception".
       Real programs will usually want finer-grained exception handling. */
    public static void main(String[] args) throws Exception {
        // Create an RMI connector client and
        // connect it to the RMI connector server
        //
        echo("\nCreate an RMI connector client and " +
                "connect it to the RMI connector server");
        var url =
                new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:9999/jmxrmi");
        var jmxc = JMXConnectorFactory.connect(url, null);

        // Create listener
        //
        var listener = new ClientListener();

        // Get an MBeanServerConnection
        //
        echo("\nGet an MBeanServerConnection");
        var mbsc = jmxc.getMBeanServerConnection();
        waitForEnterPressed();

        // Get domains from MBeanServer
        //
        echo("\nDomains:");
        String domains[] = mbsc.getDomains();
        Arrays.sort(domains);
        for (var domain : domains) {
            echo("\tDomain = " + domain);
        }
        waitForEnterPressed();

        // Get MBeanServer's default domain
        //
        echo("\nMBeanServer default domain = " + mbsc.getDefaultDomain());

        // Get MBean count
        //
        echo("\nMBean count = " + mbsc.getMBeanCount());

        // Query MBean names
        //
        echo("\nQuery MBeanServer MBeans:");
        Set<ObjectName> names =
                new TreeSet<ObjectName>(mbsc.queryNames(null, null));
        for (var name : names) {
            echo("\tObjectName = " + name);
        }
        waitForEnterPressed();

        // ----------------------
        // Manage the Hello MBean
        // ----------------------

        echo("\n>>> Perform operations on Hello MBean <<<");

        // Construct the ObjectName for the Hello MBean
        //
        var mbeanName = new ObjectName("com.example:type=Hello");

        // Create a dedicated proxy for the MBean instead of
        // going directly through the MBean server connection
        //
        var mbeanProxy =
                JMX.newMBeanProxy(mbsc, mbeanName, HelloMBean.class, true);

        // Add notification listener on Hello MBean
        //
        echo("\nAdd notification listener...");
        mbsc.addNotificationListener(mbeanName, listener, null, null);

        // Get CacheSize attribute in Hello MBean
        //
        echo("\nCacheSize = " + mbeanProxy.getCacheSize());

        // Set CacheSize attribute in Hello MBean
        // Calling "reset" makes the Hello MBean emit a
        // notification that will be received by the registered
        // ClientListener.
        //
        mbeanProxy.setCacheSize(150);

        // Sleep for 2 seconds to have time to receive the notification
        //
        echo("\nWaiting for notification...");
        sleep(2000);

        // Get CacheSize attribute in Hello MBean
        //
        echo("\nCacheSize = " + mbeanProxy.getCacheSize());

        // Invoke "sayHello" in Hello MBean
        //
        echo("\nInvoke sayHello() in Hello MBean...");
        mbeanProxy.sayHello();

        // Invoke "add" in Hello MBean
        //
        echo("\nInvoke add(2, 3) in Hello MBean...");
        echo("\nadd(2, 3) = " + mbeanProxy.add(2, 3));

        waitForEnterPressed();

        // ------------------------------
        // Manage the QueueSampler MXBean
        // ------------------------------

        echo("\n>>> Perform operations on QueueSampler MXBean <<<");

        // Construct the ObjectName for the QueueSampler MXBean
        //
        var mxbeanName =
                new ObjectName("com.example:type=QueueSampler");

        // Create a dedicated proxy for the MXBean instead of
        // going directly through the MBean server connection
        //
        var mxbeanProxy =
                JMX.newMXBeanProxy(mbsc, mxbeanName, QueueSamplerMXBean.class);

        // Get QueueSample attribute in QueueSampler MXBean
        //
        var queue1 = mxbeanProxy.getQueueSample();
        echo("\nQueueSample.Date = " + queue1.getDate());
        echo("QueueSample.Head = " + queue1.getHead());
        echo("QueueSample.Size = " + queue1.getSize());

        // Invoke "clearQueue" in QueueSampler MXBean
        //
        echo("\nInvoke clearQueue() in QueueSampler MXBean...");
        mxbeanProxy.clearQueue();

        // Get QueueSample attribute in QueueSampler MXBean
        //
        var queue2 = mxbeanProxy.getQueueSample();
        echo("\nQueueSample.Date = " + queue2.getDate());
        echo("QueueSample.Head = " + queue2.getHead());
        echo("QueueSample.Size = " + queue2.getSize());

        waitForEnterPressed();

        // Close MBeanServer connection
        //
        echo("\nClose the connection to the server");
        jmxc.close();
        echo("\nBye! Bye!");
    }

    private static void echo(String msg) {
        System.out.println(msg);
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void waitForEnterPressed() {
        try {
            echo("\nPress <Enter> to continue...");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
