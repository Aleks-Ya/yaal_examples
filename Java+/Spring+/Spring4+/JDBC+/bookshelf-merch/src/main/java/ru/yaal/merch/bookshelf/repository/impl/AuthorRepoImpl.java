package ru.yaal.merch.bookshelf.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yaal.merch.bookshelf.domain.Author;
import ru.yaal.merch.bookshelf.repository.AuthorRepo;

import java.util.List;

@Repository
class AuthorRepoImpl implements AuthorRepo {
    private static final Logger log = LoggerFactory.getLogger(AuthorRepoImpl.class);
    private static final RowMapper<Author> MAPPER = BeanPropertyRowMapper.newInstance(Author.class);
    private final SimpleJdbcInsert insert;
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public AuthorRepoImpl(@Qualifier("authors") SimpleJdbcInsert insert, NamedParameterJdbcTemplate template) {
        this.insert = insert;
        this.template = template;
    }

    @Override
    public Long insertAuthor(Author author) {
        log.debug("Insert author: " + author);
        Long id;
        if (author.getId() != null) {
            BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(author);
            template.update("INSERT INTO authors (id, name) VALUES(:id, :name)", params);
            id = author.getId();
        } else {
            SqlParameterSource map = new BeanPropertySqlParameterSource(author);
            Number key = insert.executeAndReturnKey(map);
            id = key.longValue();
        }
        log.debug("Author inserted with id: " + id);
        return id;
    }

    @Override
    public Author findById(Long authorId) {
        log.info("Find author by id " + authorId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", authorId);
        return template.queryForObject("SELECT * FROM authors WHERE id=:id", params, MAPPER);
    }

    @Override
    public List<Author> findById(List<Long> authorIds) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", authorIds);
        return template.query("SELECT * FROM authors WHERE id IN (:ids)", parameters, MAPPER);
    }

    @Override
    public List<Author> getAllAuthors() {
        return template.query("SELECT * FROM authors", MAPPER);
    }
}
