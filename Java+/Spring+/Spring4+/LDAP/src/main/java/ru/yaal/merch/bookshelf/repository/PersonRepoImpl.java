package ru.yaal.merch.bookshelf.repository;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;

import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

public class PersonRepoImpl implements PersonRepo {
    private LdapTemplate ldapTemplate;

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public List<String> getAllPersonNames() {
        return ldapTemplate.search(
                query()
                        .where("objectclass")
                        .is("person"), (AttributesMapper<String>) attrs -> (String) attrs.get("cn").get());
    }
}
