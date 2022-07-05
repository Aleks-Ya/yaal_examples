import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration("classpath:spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
class CollectionBeanTest {

    @Autowired
    ApplicationContext context;

    @Test
    void list() {
        List<MyClass> myList = (List<MyClass>) context.getBean("myList");
        assertThat(myList).containsExactlyInAnyOrder(
                new MyClass("Hello, List!"),
                new MyClass("Bay, List!")
        );
    }

    @Test
    void set() {
        Set<MyClass> mySet = (Set<MyClass>) context.getBean("mySet");
        assertThat(mySet).containsExactlyInAnyOrder(
                new MyClass("Hello, Set!"),
                new MyClass("Bay, Set!")
        );
    }

    @Test
    void map() {
        Map<String, MyClass> myMap = (Map<String, MyClass>) context.getBean("myMap");
        assertThat(myMap).containsEntry("my1", new MyClass("Hello, Map!"));
        assertThat(myMap).containsEntry("my2", new MyClass("Bay, Map!"));
    }
}