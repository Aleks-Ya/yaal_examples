package spring.boot2.oauth2.resourceserver;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
class GreetingController {
    @GetMapping("/greet")
    @PreAuthorize("isAuthenticated()")
    String greet(Authentication authentication) {
        var token = ((JwtAuthenticationToken) authentication).getToken();
        System.out.println("Received request with JWT: " + token.getTokenValue());
        return String.format("Hello from Resource Server, name=%s or client_id=%s",
                token.getClaim("name"), token.getClaim("client_id"));
    }
}
