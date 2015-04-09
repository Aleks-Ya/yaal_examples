package listeners;

import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;

public class IHookableImpl implements IHookable {
    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        System.out.println("IHookable#run");
    }
}
