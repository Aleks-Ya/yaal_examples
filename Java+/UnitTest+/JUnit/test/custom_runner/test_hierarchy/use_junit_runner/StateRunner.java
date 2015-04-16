package custom_runner.test_hierarchy.use_junit_runner;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.lang.reflect.Field;

public class StateRunner extends BlockJUnit4ClassRunner {

    private Object instance;
    private final StateHolder stateHolder = StateHolder.getInstance();
    private Class dependsOn;
    private static final String MAIN_METHOD_NAME = "main";

    public StateRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    public StateRunner(Class<?> klass, Class dependsOn) throws InitializationError {
        super(klass);
        this.dependsOn = dependsOn;
    }

    @Override
    protected Object createTest() throws Exception {
        instance = super.createTest();
        State state = stateHolder.getState(dependsOn);
        if (state != null) {
            injectState(instance, state);
        }
        return instance;
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        super.runChild(method, notifier);
        if (MAIN_METHOD_NAME.equals(method.getName())) {
            try {
                stateHolder.putState(instance.getClass(), getState(instance));
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void injectState(Object instance, State state) throws IllegalAccessException {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == State.class) {
                field.setAccessible(true);
                field.set(instance, state);
            }
        }
    }

    /**
     * Читает значение поля State данного объекта.
     */
    private State getState(Object instance) throws IllegalAccessException, InstantiationException {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == State.class) {
                field.setAccessible(true);
                return (State) field.get(instance);
            }
        }
        return null;
    }
}
