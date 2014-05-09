package provider;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("polite")
public class PoliteMessageProvider implements IMessageProvider {
    @Override
    public String getMessage() {
        return "Of course";
    }
}
