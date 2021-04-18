package databind.class_type;

import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Instantiate {@link com.fasterxml.jackson.databind.JavaType}.
 */
public class ClassTypeTest {

    @Test
    public void stringList() {
        var type = TypeFactory.defaultInstance().constructCollectionType(List.class, String.class);
        assertNotNull(type);
    }

}
