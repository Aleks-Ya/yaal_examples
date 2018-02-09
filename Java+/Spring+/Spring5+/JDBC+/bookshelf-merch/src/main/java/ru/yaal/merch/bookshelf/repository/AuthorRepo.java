package ru.yaal.merch.bookshelf.repository;

import ru.yaal.merch.bookshelf.domain.Author;

import java.util.List;

public interface AuthorRepo {
    Long insertAuthor(Author author);

    Author findById(Long authorId);

    List<Author> findById(List<Long> authorIds);

    List<Author> getAllAuthors();
}
