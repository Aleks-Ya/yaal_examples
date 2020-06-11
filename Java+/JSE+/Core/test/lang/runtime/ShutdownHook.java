package lang.runtime;

import org.junit.Test;

public class ShutdownHook {
    @Test
    public void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("bye")));
        System.out.println("hi");
    }

    @Test
    public void removeShutdownHook() {
        Thread hook = new Thread(() -> System.out.println("bye"));
        Runtime.getRuntime().addShutdownHook(hook);
        Runtime.getRuntime().removeShutdownHook(hook);
        System.out.println("hi");
    }
}
