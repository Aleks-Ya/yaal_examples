package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ITestListenerImpl implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("ITestListener#onTestStart");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("ITestListener#onTestSuccess");

    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("ITestListener#onTestFailure");

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("ITestListener#onTestSkipped");

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("ITestListener#onTestFailedButWithinSuccessPercentage");

    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("ITestListener#onStart");

    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("ITestListener#onFinish");

    }
}
