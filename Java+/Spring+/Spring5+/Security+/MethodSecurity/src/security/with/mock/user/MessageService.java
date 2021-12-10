package security.with.mock.user;

import org.springframework.security.access.prepost.PreAuthorize;

interface MessageService {
    @PreAuthorize("authenticated")
    String getMessage();
}
