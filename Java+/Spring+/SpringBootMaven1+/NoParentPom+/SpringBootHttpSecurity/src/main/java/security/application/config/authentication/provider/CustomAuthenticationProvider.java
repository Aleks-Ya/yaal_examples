package security.application.config.authentication.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final String LOGIN = "l";
    private static final String PASSWORD = "pas";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            Object principal = token.getPrincipal();
            Object credentials = token.getCredentials();
            boolean accepted = LOGIN.equalsIgnoreCase(principal.toString()) && PASSWORD.equals(credentials);
            if (accepted) {
                Collection<? extends GrantedAuthority> authority = Collections.emptyList();
                return new UsernamePasswordAuthenticationToken(principal, credentials, authority);
            } else {
                throw new BadCredentialsException(principal.toString());
            }
        } else {
            return null;// other AuthenticationProvider will be tried
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == UsernamePasswordAuthenticationToken.class;
    }

}
