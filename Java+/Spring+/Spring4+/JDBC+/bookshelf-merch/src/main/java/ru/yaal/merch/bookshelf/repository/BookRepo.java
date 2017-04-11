package ru.yaal.merch.bookshelf.repository;

import ru.yaal.merch.bookshelf.domain.Book;

import java.util.List;

public interface BookRepo {
    Long insertBook(Book book);

    Book findBookById(Long bookId);

    List<Book> findBooksByName(String substring);

    List<Book> findBooksByAuthor(Long authorId);
}
