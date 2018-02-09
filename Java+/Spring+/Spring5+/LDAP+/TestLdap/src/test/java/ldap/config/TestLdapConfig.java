package ldap.config;

import ldap.repository.PersonRepoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.test.TestContextSourceFactoryBean;

@Configuration
@ComponentScan(basePackageClasses = PersonRepoImpl.class)
public class TestLdapConfig {

    @Bean
    public LdapTemplate ldapTemplate(ContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }

    @Bean
    public TestContextSourceFactoryBean testContextSourceFactoryBean() {
        TestContextSourceFactoryBean bean = new TestContextSourceFactoryBean();
        bean.setDefaultPartitionSuffix("dc=jayway,dc=se");
        bean.setDefaultPartitionName("jayway");
        bean.setPrincipal("uid=admin,ou=system");
        bean.setPassword("secret");
        bean.setLdifFile(new ClassPathResource("setup_data.ldif"));
        bean.setPort(1888);
        return bean;
    }
}
