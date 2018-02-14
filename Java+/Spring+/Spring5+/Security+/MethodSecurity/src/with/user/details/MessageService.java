package with.user.details;

import org.springframework.security.access.prepost.PreAuthorize;

interface MessageService {
    @PreAuthorize("authenticated")
    String getMessage();
}
