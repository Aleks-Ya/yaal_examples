package lang.reflection.callable_executor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.Callable;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CallableExecutor {
    public Class<? extends Callable> callable();
}
