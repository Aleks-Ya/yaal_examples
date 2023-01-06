import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

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