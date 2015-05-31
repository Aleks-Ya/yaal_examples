package ascii;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author Aleksey Yablokov.
 */
@Target(ElementType.FIELD)
@Documented
public @interface Name {
    String value();
}
