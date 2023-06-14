package security.authentication.testing;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
class SecuredService {
    @PreAuthorize("hasRole('MANAGER_ROLE')")
    String getSecretWord() {
        return "peace";
    }
}
