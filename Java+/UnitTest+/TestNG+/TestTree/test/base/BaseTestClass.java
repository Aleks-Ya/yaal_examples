package base;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseTestClass {
    protected String title;
    private static final SaveRestoreHandler handler = new SaveRestoreHandler();

    @BeforeMethod
    public void initState() {
        String className = getClass().getName();
        String dependencyGroup = AnnotationListener.classDependsOnGroup.get(className);
        State state = handler.load(dependencyGroup);
        title = state.getTitle();
    }

    @AfterMethod
    public void saveState(Method method) {
        String className = getClass().getName();
        String groupName = AnnotationListener.classNameToGroup.get(className);
        String methodName = className + "#" + method.getName();
        if (groupName != null && AnnotationListener.mainMethods.contains(methodName)) {
            handler.save(groupName, new State(title));
        }
    }
}