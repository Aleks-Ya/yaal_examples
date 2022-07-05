package xml;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration({
        "classpath:xml/spring-context.xml"
})
class BookTest {
    @Autowired
    private Book book;

    @Test
    void title() {
        assertThat(book.getTitle()).isEqualTo("Java. Effective programming");
    }
}
