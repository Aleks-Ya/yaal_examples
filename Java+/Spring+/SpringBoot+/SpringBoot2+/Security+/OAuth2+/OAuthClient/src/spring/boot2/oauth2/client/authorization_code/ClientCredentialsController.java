package spring.boot2.oauth2.client.authorization_code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@RestController
@RequestMapping("/clientCredentials")
class ClientCredentialsController {
    @Autowired
    private WebClient webClient;
    @Autowired
    private AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceAndManager;

    @GetMapping("/localResource")
    String localResource(@AuthenticationPrincipal OAuth2User principal) {
        return "Hello, " + principal.getAttribute("name");
    }

    /**
     * Get OAuth2AuthorizedClient with annotation @RegisteredOAuth2AuthorizedClient.
     */
    @GetMapping("/resourceServer/autowire")
    String getResourceFromResourceServer_autowire(@RegisteredOAuth2AuthorizedClient("keycloak-client-credentials")
                                                  OAuth2AuthorizedClient authorizedClient) {
        return webClient
                .get()
                .uri("http://localhost:8082/api/greet")
                .headers(headers -> headers.setBearerAuth(authorizedClient.getAccessToken().getTokenValue()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    /**
     * Instantiate OAuth2AuthorizedClient manually.
     */
    @GetMapping("/resourceServer/manual")
    String getResourceFromResourceServer_manual() {
        var authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("keycloak-client-credentials")
                .principal("Demo Service")
                .build();
        var authorizedClient = authorizedClientServiceAndManager.authorize(authorizeRequest);
        var accessToken = Objects.requireNonNull(authorizedClient).getAccessToken();
        var tokenValue = accessToken.getTokenValue();
        System.out.println("Got access token manually: " + tokenValue);
        return webClient
                .get()
                .uri("http://localhost:8082/api/greet")
                .headers(headers -> headers.setBearerAuth(tokenValue))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

