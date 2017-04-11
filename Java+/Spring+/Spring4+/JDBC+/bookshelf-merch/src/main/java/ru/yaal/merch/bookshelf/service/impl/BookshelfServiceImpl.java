package ru.yaal.merch.bookshelf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.yaal.merch.bookshelf.domain.Author;
import ru.yaal.merch.bookshelf.domain.Book;
import ru.yaal.merch.bookshelf.repository.AuthorRepo;
import ru.yaal.merch.bookshelf.repository.BookRepo;
import ru.yaal.merch.bookshelf.repository.BookToAuthorRepo;
import ru.yaal.merch.bookshelf.service.BookshelfService;

import java.util.List;

@Service
class BookshelfServiceImpl implements BookshelfService {
    private final AuthorRepo authorRepo;
    private final BookRepo bookRepo;
    private final BookToAuthorRepo bookToAuthorRepo;

    @Autowired
    public BookshelfServiceImpl(AuthorRepo authorRepo, BookRepo bookRepo, BookToAuthorRepo bookToAuthorRepo) {
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
        this.bookToAuthorRepo = bookToAuthorRepo;
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void storeBook(Book book) {
        bookRepo.insertBook(book);
        List<Author> authors = book.getAuthors();
        if (authors != null) {
            for (Author author : authors) {
                authorRepo.insertAuthor(author);
                bookToAuthorRepo.insert(book.getId(), author.getId());
            }
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Book> booksByAuthor(Long authorId) {
        List<Book> books = bookRepo.findBooksByAuthor(authorId);
        for (Book book : books) {
            List<Long> authorIds = bookToAuthorRepo.findAuthorIdsByBook(authorId);
            List<Author> authors = authorRepo.findById(authorIds);
            book.setAuthors(authors);
        }
        return books;
    }

    @Override
    public List<Book> searchByTitle(String substring) {
        return bookRepo.findBooksByName(substring);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepo.getAllAuthors();
    }
}
