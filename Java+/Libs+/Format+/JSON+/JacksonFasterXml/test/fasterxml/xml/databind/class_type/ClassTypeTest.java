package fasterxml.xml.databind.class_type;

import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Instantiate {@link com.fasterxml.jackson.databind.JavaType}.
 */
class ClassTypeTest {

    @Test
    void stringList() {
        var type = TypeFactory.defaultInstance().constructCollectionType(List.class, String.class);
        assertThat(type).isNotNull();
    }

}
