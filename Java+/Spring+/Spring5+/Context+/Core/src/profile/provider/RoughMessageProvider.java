package profile.provider;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("rough")
class RoughMessageProvider implements IMessageProvider {
    @Override
    public String getMessage() {
        return "fuck off";
    }
}
