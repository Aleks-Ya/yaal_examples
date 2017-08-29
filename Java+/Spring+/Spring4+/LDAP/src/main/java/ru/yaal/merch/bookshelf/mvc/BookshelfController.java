package ru.yaal.merch.bookshelf.mvc;

import ru.yaal.merch.bookshelf.domain.Author;
import ru.yaal.merch.bookshelf.domain.Book;

import java.util.List;

public interface BookshelfController {
    List<Book> findBooksByAuthor(Long authorId);

    List<Book> findBookByTitle(String substring);

    void addBook(Book book);

    List<Author> getAllAuthors();
}
