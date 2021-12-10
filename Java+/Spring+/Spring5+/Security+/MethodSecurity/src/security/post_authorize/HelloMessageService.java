package security.post_authorize;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
class HelloMessageService implements MessageService {
    public Message getMessage(String recipientName) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return new Message(recipientName, "Hello, " + authentication.getName());
    }
}