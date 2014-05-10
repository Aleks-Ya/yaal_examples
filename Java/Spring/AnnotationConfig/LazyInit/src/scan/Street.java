package scan;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Street {
    public Street() {
        System.out.println("Street is constructed.");
    }
}
