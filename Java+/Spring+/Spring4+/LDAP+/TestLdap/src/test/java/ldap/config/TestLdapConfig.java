package ldap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.test.EmbeddedLdapServerFactoryBean;
import org.springframework.ldap.test.TestContextSourceFactoryBean;
import ldap.repository.PersonRepoImpl;

@Configuration
@ComponentScan(basePackageClasses = PersonRepoImpl.class)
public class TestLdapConfig {
//    @Bean
//    public ContextSource ldapContextSource() {
//        LdapContextSource contextSource = new LdapContextSource();
//        contextSource.setUrl("ldap://ldap.forumsys.com:389");
////        contextSource.setBase("dc=example,dc=com");
//        contextSource.setUserDn("cn=read-only-admin,dc=example,dc=com");
//        contextSource.setPassword("password");
//        contextSource.afterPropertiesSet(); // *** need this ***
//        return contextSource;
//    }

    @Bean
    public LdapTemplate ldapTemplate(ContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }

    @Bean
    public EmbeddedLdapServerFactoryBean embeddedLdapServerFactoryBean() {
        EmbeddedLdapServerFactoryBean bean = new EmbeddedLdapServerFactoryBean();
        bean.setPartitionName("example");
        bean.setPartitionSuffix("dc=261consulting,dc=com");
        bean.setPort(9321);
        return bean;
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

//    @Bean
//    @DependsOn("embeddedLdapServerFactoryBean")
//    public LdifPopulator ldifPopulator(ContextSource contextSource) {
//        LdifPopulator bean = new LdifPopulator();
//        bean.setContextSource(contextSource);
//        bean.setResource(new ClassPathResource("setup_data.ldif"));
//        bean.setBase("dc=jayway,dc=se");
//        bean.setClean(true);
//        bean.setDefaultBase("dc=jayway,dc=se");
//        return bean;
//    }
}
