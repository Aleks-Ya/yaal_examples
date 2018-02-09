package ru.yaal.merch.bookshelf.repository;

import java.util.List;

public interface BookToAuthorRepo {
    void insert(Long bookId, Long authorId);

    List<Long> findAuthorIdsByBook(Long bookId);
}
