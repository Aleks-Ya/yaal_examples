package xml;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@ContextConfiguration({
        "classpath:xml/spring-context.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class BookTest {
    @Autowired
    private Book book;

    @Test
    public void title() {
        assertEquals(book.getTitle(), "Java. Effective programming");
    }
}
