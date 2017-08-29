package ru.yaal.merch.bookshelf.mvc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yaal.merch.bookshelf.domain.Author;
import ru.yaal.merch.bookshelf.domain.Book;
import ru.yaal.merch.bookshelf.mvc.BookshelfController;
import ru.yaal.merch.bookshelf.service.BookshelfService;

import java.util.List;

@RestController
class BookshelfControllerImpl implements BookshelfController {

    private final BookshelfService service;

    @Autowired
    public BookshelfControllerImpl(BookshelfService service) {
        this.service = service;
    }

    @Override
    @RequestMapping(value = "/book/{authorId}")
    public List<Book> findBooksByAuthor(@PathVariable Long authorId) {
        return service.booksByAuthor(authorId);
    }

    @Override
    @RequestMapping(value = "/book/find")
    public List<Book> findBookByTitle(@RequestParam("query") String substring) {
        return service.searchByTitle(substring);
    }

    @Override
    @RequestMapping(value = "/book", consumes = "application/json", method = RequestMethod.POST)
    public void addBook(@RequestBody Book book) {
        service.storeBook(book);
    }

    @Override
    @RequestMapping(value = "/author")
    public List<Author> getAllAuthors() {
        return service.getAllAuthors();
    }
}
