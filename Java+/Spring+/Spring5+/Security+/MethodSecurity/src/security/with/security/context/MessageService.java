package security.with.security.context;

import org.springframework.security.access.prepost.PreAuthorize;

interface MessageService {
    @PreAuthorize("authenticated")
    String getMessage();
}
