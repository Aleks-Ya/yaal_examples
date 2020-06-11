package lang.runtime;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

/**
 * Handle kill signals from OS.
 */
public class KillHanding {

    public static void main(String[] args) {
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        System.out.println("Kill soft (hook is invoked):");
        System.out.println("1. SIGINT == Ctrl-C: 'kill -2 " + pid + "'");
        System.out.println("2. SIGTERM: 'kill -15 " + pid + "'\n");
        System.out.println("Kill hard (hook is NOT invoked):");
        System.out.println("1. SIGPWR: 'kill -30 " + pid + "'");
        System.out.println("2. SIGKILL: 'kill -9 " + pid + "'\n");
        System.out.println("Doesn't work:");
        System.out.println("1. SIGQUIT == Ctrl-\\: 'kill -3 " + pid + "'");
        System.out.println("2. SIGTSTP == Ctrl-Z 1st: 'kill -20 " + pid + "'");
        System.out.println("3. SIGCONT == Ctrl-Z 2st: 'kill -18 " + pid + "'");

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> System.out.println("================= BYE! =================")));

        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
