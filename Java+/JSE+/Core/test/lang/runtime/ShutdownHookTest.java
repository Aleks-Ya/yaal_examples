package lang.runtime;

import org.junit.jupiter.api.Test;

class ShutdownHookTest {
    @Test
    void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("bye")));
        System.out.println("hi");
    }

    @Test
    void removeShutdownHook() {
        Thread hook = new Thread(() -> System.out.println("bye"));
        Runtime.getRuntime().addShutdownHook(hook);
        Runtime.getRuntime().removeShutdownHook(hook);
        System.out.println("hi");
    }
}
