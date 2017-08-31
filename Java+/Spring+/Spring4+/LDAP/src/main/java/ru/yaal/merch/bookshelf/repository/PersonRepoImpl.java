package ru.yaal.merch.bookshelf.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Component
public class PersonRepoImpl implements PersonRepo {
    private final LdapTemplate ldapTemplate;

    @Autowired
    public PersonRepoImpl(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    @Override
    public List<String> getAllPersonNames() {
        return ldapTemplate.search(
                query()
                        .base("dc=example,dc=com")
                        .where("cn")
                        .like("Nikola Tesla"), (AttributesMapper<String>) attrs -> (String) attrs.get("cn").get());
    }
}
