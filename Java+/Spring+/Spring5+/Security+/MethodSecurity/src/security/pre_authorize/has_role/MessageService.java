package security.pre_authorize.has_role;

import org.springframework.security.access.prepost.PreAuthorize;

interface MessageService {
    @PreAuthorize("hasRole('RECEIVER')")
    String getMessage();
}
