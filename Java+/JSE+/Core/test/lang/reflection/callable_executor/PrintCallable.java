package lang.reflection.callable_executor;

import java.util.concurrent.Callable;

public class PrintCallable implements Callable<Void> {
    @Override
    public Void call() throws Exception {
        System.out.println("PrintCallable");
        return null;
    }
}
