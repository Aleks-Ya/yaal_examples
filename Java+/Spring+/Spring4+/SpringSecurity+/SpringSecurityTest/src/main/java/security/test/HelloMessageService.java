package security.test;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
class HelloMessageService implements MessageService {
    public String getMessage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Hello " + authentication;
    }
}