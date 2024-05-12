package spring.boot3.oauth2.client.authorization_code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/authorizationCode")
class AuthorizationCodeController {
    @Autowired
    private WebClient webClient;

    @GetMapping("/localResource")
    String localResource(@AuthenticationPrincipal OAuth2User principal) {
        return "Hello, " + principal.getAttribute("name");
    }

    @GetMapping("/resourceServer")
    String getResourceFromResourceServer(@RegisteredOAuth2AuthorizedClient("keycloak-authorization-code")
                                         OAuth2AuthorizedClient authorizedClient) {
        return this.webClient
                .get()
                .uri("http://localhost:8082/api/greet")
                .headers(headers -> headers.setBearerAuth(authorizedClient.getAccessToken().getTokenValue()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

