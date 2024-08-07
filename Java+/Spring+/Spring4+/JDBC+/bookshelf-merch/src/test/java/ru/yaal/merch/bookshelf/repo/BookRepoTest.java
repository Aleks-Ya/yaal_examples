package ru.yaal.merch.bookshelf.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.yaal.merch.bookshelf.config.RepoTestConfig;
import ru.yaal.merch.bookshelf.domain.Book;
import ru.yaal.merch.bookshelf.repository.BookRepo;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RepoTestConfig.class})
public class BookRepoTest {

    @Autowired
    private BookRepo repo;

    @Test
    void findByAuthor() {
        long authorId = 3L;
        List<Book> books = repo.findBooksByAuthor(authorId);
        assertThat(books).hasSize(2));
    }

    @Test
    void insert() {
        Book expected = new Book(null, "my book", "abstract", null);
        Long id = repo.insertBook(expected);
        expected.setId(id);
        Book actual = repo.findBookById(id);
        assertThat(actual).isEqualTo(expected));
    }

    @Test
    void select() {
        long id = 1000L;
        Book expected = new Book(id, "10 book", "abstract10", null);
        repo.insertBook(expected);
        Book actual = repo.findBookById(id);
        assertThat(actual).isEqualTo(expected));
    }

    @Test
    void findByName() {
        Book expected = new Book(20L, "20 find book", "abstract20", null);
        repo.insertBook(expected);
        Book expected2 = new Book(22L, "22 find book", "abstract22", null);
        repo.insertBook(expected2);
        repo.insertBook(new Book(21L, "21 book", "abstract21", null));
        List<Book> actual = repo.findBooksByName("find");
        assertThat(actual).containsExactlyInAnyOrder(expected, expected2));
    }
}