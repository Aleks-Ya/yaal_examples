package ru.yaal.merch.bookshelf.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yaal.merch.bookshelf.domain.Book;
import ru.yaal.merch.bookshelf.repository.BookRepo;

import java.util.List;

@Repository
class BookRepoImpl implements BookRepo {
    private static final Logger log = LoggerFactory.getLogger(BookRepoImpl.class);
    private static final RowMapper<Book> MAPPER = BeanPropertyRowMapper.newInstance(Book.class);
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    @Autowired
    public BookRepoImpl(JdbcTemplate template, @Qualifier("books") SimpleJdbcInsert insert) {
        this.template = template;
        this.insert = insert;
    }

    @Override
    public Long insertBook(Book book) {
        log.debug("Inset book " + book);
        Long id;
        if (book.getId() != null) {
            template.update("INSERT INTO books(id, name, abstractPart) VALUES(?,?,?)",
                    book.getId(), book.getName(), book.getAbstractPart());
            id = book.getId();
        } else {
            SqlParameterSource map = new BeanPropertySqlParameterSource(book);
            Number key = insert.executeAndReturnKey(map);
            id = key.longValue();
        }
        log.debug("Book inserted with id: " + id);
        return id;
    }

    @Override
    public Book findBookById(Long bookId) {
        log.debug("Find book by id " + bookId);
        return template.queryForObject("SELECT * FROM books WHERE id=?", new Long[]{bookId}, MAPPER);
    }

    @Override
    public List<Book> findBooksByName(String substring) {
        substring = "%" + substring + "%";
        log.debug("findBooksByName '{}'", substring);
        List<Book> found = template.query("SELECT * FROM books WHERE name LIKE ?", MAPPER, substring);
        log.debug("Books are found: " + found);
        return found;
    }

    @Override
    public List<Book> findBooksByAuthor(Long authorId) {
        log.debug("Find book by author " + authorId);
        return template.query(
                "SELECT * FROM books JOIN book_to_author ON id = bookId WHERE authorId = ?", MAPPER, authorId);
    }

}
