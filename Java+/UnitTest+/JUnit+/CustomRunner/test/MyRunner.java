import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

public class MyRunner extends Runner {
    public MyRunner(Class testClass) {
        this.testClass = testClass;
    }

    private Class testClass;

    @Override
    public Description getDescription() {
        Description description = Description.createSuiteDescription(testClass);
        return description;
    }

    @Override
    public void run(RunNotifier notifier) {

        "".toString();
    }
}
