import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ITestRunnerFactory;
import org.testng.TestRunner;
import org.testng.xml.XmlTest;

import java.util.List;

public class MyTestRunnerFactory implements ITestRunnerFactory {
    @Override
    public TestRunner newTestRunner(ISuite suite, XmlTest test, List<IInvokedMethodListener> listeners) {
        System.out.println("MyTestRunnerFactory works");
        System.exit(333);
        return new TestRunner(null, suite, test, false, listeners);
    }
}
