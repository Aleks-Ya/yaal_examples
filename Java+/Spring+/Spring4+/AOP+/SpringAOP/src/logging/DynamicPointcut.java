package logging;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

import java.lang.reflect.Method;

public class DynamicPointcut extends DynamicMethodMatcherPointcut {
    private static final Class[] targetClasses = {
            IWork.class
    };

    @Override
    public ClassFilter getClassFilter() {
        return new ClassFilter() {
            @Override
            public boolean matches(Class<?> clazz) {
                for (Class<?> targetClass : targetClasses) {
                    if (targetClass.isAssignableFrom(clazz)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object[] args) {
        return true;
    }
}
