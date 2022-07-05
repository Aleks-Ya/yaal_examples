package xml;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration({
        "classpath:xml/spring-context.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
class BookTest {
    @Autowired
    private Book book;

    @Test
    void title() {
        assertThat(book.getTitle()).isEqualTo("Java. Effective programming");
    }
}
