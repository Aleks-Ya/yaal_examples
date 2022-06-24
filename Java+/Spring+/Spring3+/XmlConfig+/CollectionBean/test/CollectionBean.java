import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasEntry;

@ContextConfiguration("classpath:spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CollectionBean {

    @Autowired
    ApplicationContext context;

    @Test
    void list() {
        List<MyClass> myList = (List<MyClass>) context.getBean("myList");
        assertThat(myList, containsInAnyOrder(
                new MyClass("Hello, List!"),
                new MyClass("Bay, List!"))
        );
    }

    @Test
    void set() {
        Set<MyClass> mySet = (Set<MyClass>) context.getBean("mySet");
        assertThat(mySet, containsInAnyOrder(
                new MyClass("Hello, Set!"),
                new MyClass("Bay, Set!"))
        );
    }

    @Test
    void map() {
        Map<String, MyClass> myMap = (Map<String, MyClass>) context.getBean("myMap");
        assertThat(myMap, hasEntry("my1", new MyClass("Hello, Map!")));
        assertThat(myMap, hasEntry("my2", new MyClass("Bay, Map!")));
    }
}