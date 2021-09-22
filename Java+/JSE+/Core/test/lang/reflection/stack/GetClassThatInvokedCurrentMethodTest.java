package lang.reflection.stack;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//import sun.reflect.Reflection;

/**
 * Get class that invoked the current method.
 */
class GetClassThatInvokedCurrentMethodTest {

//    private static Class<?> getCallerClass() {
//        return Reflection.getCallerClass(2);
//    }

    @Test
    @Disabled("Require Java 9")
    public void stackWalker() {
//        StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
//        Optional<Class<?>> callerClass = walker.walk(s ->
//                s.map(StackWalker.StackFrame::getDeclaringClass)
//                        .filter(interestingClasses::contains)
//                        .findFirst());
    }

    @Test
    @Disabled("doesn't work")
    public void sunReflectReflection() {
//        Class<?> callerClass = TheCaller.call();
//        assertThat(callerClass, Matchers.is(TheCaller.class));
    }

    private static class TheCaller {
//        static Class<?> call() {
//            return getCallerClass();
//        }
    }
}
