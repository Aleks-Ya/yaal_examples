package ru.yaal.merch.bookshelf.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.yaal.merch.bookshelf.config.RepoTestConfig;
import ru.yaal.merch.bookshelf.domain.Author;
import ru.yaal.merch.bookshelf.repository.AuthorRepo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RepoTestConfig.class})
public class AuthorRepoTest {

    @Autowired
    private AuthorRepo repo;

    @Test
    void insert() {
        Author expected = new Author(null, "Conan Doyle");
        Long id = repo.insertAuthor(expected);
        expected.setId(id);
        Author actual = repo.findById(id);
        assertThat(actual).isEqualTo(expected));
    }

}