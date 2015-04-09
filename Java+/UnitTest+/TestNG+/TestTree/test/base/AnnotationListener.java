package base;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationListener implements IAnnotationTransformer {
    public static final Map<String, String> classNameToGroup = new HashMap<String, String>();
    public static final Map<String, String> classDependsOnGroup = new HashMap<String, String>();
    public static final List<String> mainMethods = new ArrayList<String>();

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        if (testClass == null) {
            String className = testMethod.getDeclaringClass().getName();
            String[] curGroups = annotation.getGroups();
            if (curGroups != null && curGroups.length > 0) {
                if (curGroups.length > 1) {
                    throw new RuntimeException(">1 groups: " + className);
                }
                mainMethods.add(className + "#" + testMethod.getName());
                classNameToGroup.put(className, curGroups[0]);
            }

        } else {
            String[] dependsOnGroups = annotation.getDependsOnGroups();
            if (dependsOnGroups != null && dependsOnGroups.length > 0) {
                if (dependsOnGroups.length > 1) {
                    throw new RuntimeException("Depends on >1 groups: " + testClass.getName());
                }
                classDependsOnGroup.put(testClass.getName(), dependsOnGroups[0]);
            }
        }
    }
}
