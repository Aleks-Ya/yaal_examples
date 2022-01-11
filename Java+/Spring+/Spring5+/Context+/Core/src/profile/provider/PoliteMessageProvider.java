package profile.provider;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("polite")
class PoliteMessageProvider implements IMessageProvider {
    @Override
    public String getMessage() {
        return "Of course";
    }
}
