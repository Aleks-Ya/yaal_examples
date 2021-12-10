package security.post_authorize;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

interface MessageService {
    @PreAuthorize("hasRole('RECEIVER')")
    @PostAuthorize("returnObject.recipient == authentication.name")
    Message getMessage(String recipientName);
}

