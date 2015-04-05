import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.List;

/**
 * JMX-клиент, позволяющий управлять логированием в классе Cycled.
 */
public class JmxClient {
    private static final String STRING_CLASS_NAME = String.class.getName();

    public static void main(String[] args) throws IOException, MalformedObjectNameException, IntrospectionException, InstanceNotFoundException, ReflectionException, AttributeNotFoundException, MBeanException {
        JMXServiceURL url =
                new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:9999/jmxrmi");
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

        ObjectName jmxConfiguratorName = new ObjectName("ch.qos.logback.classic:Name=default,Type=ch.qos.logback.classic.jmx.JMXConfigurator");
        try {
            if (args == null || args.length == 0) {
                printLoggerLevels(mbsc, jmxConfiguratorName);
            } else {
                switch (args.length) {
                    case 1: {
                        switch (args[0]) {
                            case "print-levels": {
                                printLoggerLevels(mbsc, jmxConfiguratorName);
                                break;
                            }
                            default: {
                                throw new WrongCommand(args[0]);
                            }
                        }
                        break;
                    }
                    case 2: {
                        switch (args[0]) {
                            case "set-levels": {
                                System.out.println("BEFORE");
                                printLoggerLevels(mbsc, jmxConfiguratorName);
                                System.out.println();

                                setAllLoggersLevel(mbsc, jmxConfiguratorName, args[1]);

                                System.out.println("AFTER");
                                printLoggerLevels(mbsc, jmxConfiguratorName);
                                System.out.println();
                                break;
                            }
                            default: {
                                throw new WrongCommand(args[0]);
                            }
                        }
                        break;
                    }
                    default: {
                        throw new WrongCommand(args[0]);
                    }
                }

            }
        } catch (WrongCommand e) {
            System.out.println("Wrong command: " + e.getCommand());
        }
    }

    private static void printLoggerLevels(MBeanServerConnection mbsc, ObjectName jmxConfiguratorName)
            throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException,
            IOException, MalformedObjectNameException {
        List<String> loggerNames = (List<String>) mbsc.getAttribute(jmxConfiguratorName, "LoggerList");
        System.out.println("Logger levels:");
        for (String loggerName : loggerNames) {
            String level = (String) mbsc.invoke(jmxConfiguratorName, "getLoggerEffectiveLevel",
                    new String[]{loggerName}, new String[]{STRING_CLASS_NAME});
            System.out.printf("%s: %s%n", loggerName, level);
        }
    }

    private static void setLoggerLevel(MBeanServerConnection mbsc, ObjectName jmxConfiguratorName,
                                       String loggerName, String newLevel)
            throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException,
            IOException, MalformedObjectNameException {
        mbsc.invoke(jmxConfiguratorName, "setLoggerLevel", new String[]{loggerName, newLevel},
                new String[]{STRING_CLASS_NAME, STRING_CLASS_NAME});
    }

    private static void setAllLoggersLevel(MBeanServerConnection mbsc, ObjectName jmxConfiguratorName, String newLevel)
            throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException,
            IOException, MalformedObjectNameException {
        List<String> loggerNames = (List<String>) mbsc.getAttribute(jmxConfiguratorName, "LoggerList");
        for (String loggerName : loggerNames) {
            setLoggerLevel(mbsc, jmxConfiguratorName, loggerName, newLevel);
        }
    }

    private static final class WrongCommand extends Exception {
        private String command;

        public WrongCommand(String command) {
            this.command = command;
        }

        public String getCommand() {
            return command;
        }
    }
}
