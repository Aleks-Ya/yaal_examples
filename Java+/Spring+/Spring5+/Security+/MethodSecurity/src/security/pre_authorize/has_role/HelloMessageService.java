package security.pre_authorize.has_role;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
class HelloMessageService implements MessageService {
    public String getMessage() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Hello, " + authentication.getName();
    }
}