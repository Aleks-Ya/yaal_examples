package rst.config;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithOAuth2AuthenticationSecurityContextFactory implements WithSecurityContextFactory<WithOAuth2Authentication> {

    public WithOAuth2AuthenticationSecurityContextFactory(OAuthHelper helper) {
        this.helper = helper;
    }

    OAuthHelper helper;

    @Override
    public SecurityContext createSecurityContext(final WithOAuth2Authentication user) {
        OAuth2Authentication oAuth2Authentication = helper.oAuth2Authentication(user.clientId(), user.username());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(oAuth2Authentication);
        return context;
    }
}
