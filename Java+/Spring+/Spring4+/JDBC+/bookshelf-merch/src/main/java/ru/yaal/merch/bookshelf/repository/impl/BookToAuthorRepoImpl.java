package ru.yaal.merch.bookshelf.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yaal.merch.bookshelf.repository.BookToAuthorRepo;

import java.util.List;

@Repository
class BookToAuthorRepoImpl implements BookToAuthorRepo {
    private static final Logger log = LoggerFactory.getLogger(BookToAuthorRepoImpl.class);
    private final JdbcTemplate template;

    @Autowired
    public BookToAuthorRepoImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void insert(Long bookId, Long authorId) {
        log.debug("Insert relation bookId:{} - authorId:{}", bookId, authorId);
        template.update("INSERT INTO book_to_author(bookId, authorId) VALUES(?,?)", bookId, authorId);
    }

    @Override
    public List<Long> findAuthorIdsByBook(Long bookId) {
        return template.queryForList("SELECT authorId FROM book_to_author WHERE bookId = ?", Long.class, bookId);
    }
}
