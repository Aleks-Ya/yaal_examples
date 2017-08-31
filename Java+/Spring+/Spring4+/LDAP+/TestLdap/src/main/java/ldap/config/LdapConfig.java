package ldap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapConfig {
    @Bean
    public ContextSource ldapContextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://ldap.forumsys.com:389");
//        contextSource.setBase("dc=example,dc=com");
        contextSource.setUserDn("cn=read-only-admin,dc=example,dc=com");
        contextSource.setPassword("password");
        contextSource.afterPropertiesSet(); // *** need this ***
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate(ContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }
}
