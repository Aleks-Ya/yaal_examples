package custom_runner.test_hierarchy.rule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.annotation.Annotation;

public class DependsOnRule implements TestRule {

    @Override
    public Statement apply(Statement base, Description description) {
        Class dependsOn = getDependsOnValue(description.getTestClass());

        if (dependsOn != null) {
            try {
                Statement st = new Statement() {
                    @Override
                    public void evaluate() throws Throwable {

                    }
                };
                Runner runner = new BlockJUnit4ClassRunner(dependsOn);
//                runner.run();
            } catch (InitializationError e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }

    /**
     * Возвращает класс, указанный в @DependsOn.
     */
    private Class getDependsOnValue(Class klass) {
        Annotation dependsOnAnnotation = klass.getAnnotation(DependsOn.class);
        if (dependsOnAnnotation != null) {
            try {
                return (Class) dependsOnAnnotation.getClass().getMethod("value").invoke(dependsOnAnnotation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
