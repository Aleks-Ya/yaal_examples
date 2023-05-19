/*
 * Main.java - main class for the Hello MBean and QueueSampler MXBean example.
 * Create the Hello MBean and QueueSampler MXBean, register them in the platform
 * MBean server, then wait forever (or until the program is interrupted).
 */

package lang.management.jmx.custom_jmx_client;

import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Example from <a href="http://docs.oracle.com/javase/tutorial/jmx/remote/custom.html">JSE Tutorial</a>.<br/>
 * Run:
 * java -Dcom.sun.management.jmxremote.port=9999 \
 * -Dcom.sun.management.jmxremote.authenticate=false \
 * -Dcom.sun.management.jmxremote.ssl=false \
 * com.example.Main
 */
public class Main {
    /* For simplicity, we declare "throws Exception".
       Real programs will usually want finer-grained exception handling. */
    public static void main(String[] args) throws Exception {
        // Get the Platform MBean Server
        var mbs = ManagementFactory.getPlatformMBeanServer();

        // Construct the ObjectName for the Hello MBean we will register
        var mbeanName = new ObjectName("com.example:type=Hello");

        // Create the Hello World MBean
        var mbean = new Hello();

        // Register the Hello World MBean
        mbs.registerMBean(mbean, mbeanName);

        // Construct the ObjectName for the QueueSampler MXBean we will register
        var mxbeanName = new ObjectName("com.example:type=QueueSampler");

        // Create the Queue Sampler MXBean
        Queue<String> queue = new ArrayBlockingQueue<String>(10);
        queue.add("Request-1");
        queue.add("Request-2");
        queue.add("Request-3");
        var mxbean = new QueueSampler(queue);

        // Register the Queue Sampler MXBean
        mbs.registerMBean(mxbean, mxbeanName);

        // Wait forever
        System.out.println("Waiting for incoming requests...");
        Thread.sleep(Long.MAX_VALUE);
    }
}
