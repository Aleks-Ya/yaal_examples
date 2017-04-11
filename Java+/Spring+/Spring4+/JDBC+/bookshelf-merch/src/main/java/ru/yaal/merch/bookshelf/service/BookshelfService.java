package ru.yaal.merch.bookshelf.service;

import ru.yaal.merch.bookshelf.domain.Author;
import ru.yaal.merch.bookshelf.domain.Book;

import java.util.List;

public interface BookshelfService {
    void storeBook(Book book);

    List<Book> booksByAuthor(Long authorId);

    List<Book> searchByTitle(String substring);

    List<Author> getAllAuthors();
}
