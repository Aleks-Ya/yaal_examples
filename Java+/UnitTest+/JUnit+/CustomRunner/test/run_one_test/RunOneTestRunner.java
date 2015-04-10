package run_one_test;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * Запустить одиночный тестовый класс из самописного Runner.
 */
public class RunOneTestRunner extends Runner {
    private final Class testClass;
    private final Description description;

    public RunOneTestRunner(Class testClass) {
        this.testClass = testClass;
        description = Description.createSuiteDescription(testClass);
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            Runner runner = new BlockJUnit4ClassRunner(testClass);
            runner.run(notifier);
        } catch (Throwable error) {
            notifier.fireTestFailure(new Failure(description, error));
        }
    }
}