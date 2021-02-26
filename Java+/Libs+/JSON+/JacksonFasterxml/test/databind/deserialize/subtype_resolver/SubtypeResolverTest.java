package databind.deserialize.subtype_resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static org.junit.Assert.assertEquals;

/**
 * Polymorphic Deserialization
 */
public class SubtypeResolverTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void parent() throws IOException {
        var expParent = new Parent();
        expParent.setName("my name");
        var parentJson = mapper.writeValueAsString(expParent);
        assertEquals("{\"name\":\"my name\"}", parentJson);
        var actParent = mapper.readValue(parentJson, Parent.class);
        assertEquals(expParent, actParent);
    }

    @Test
    public void child() throws IOException {
        var expChild = new Child();
        expChild.setName("my name");
        expChild.setNumber(1);
        var childJson = mapper.writeValueAsString(expChild);
        assertEquals("{\"name\":\"my name\",\"number\":1}", childJson);
        var actChild = mapper.readValue(childJson, Child.class);
        assertEquals(expChild, actChild);
    }

    @Test
    public void defaultTyping() throws IOException {
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        var expChild = new Child();
        expChild.setName("my name");
        expChild.setNumber(1);
        var childJson = mapper.writeValueAsString(expChild);
        var expJson = "['databind.deserialize.subtype_resolver.SubtypeResolverTest$Child',{name:'my name',number:1}]";
        assertJsonEquals(expJson, childJson);
        var actChild = mapper.readValue(childJson, Parent.class);
        assertEquals(expChild, actChild);
    }

    private static class Parent {
        private String name;

        @SuppressWarnings("WeakerAccess")
        public String getName() {
            return name;
        }

        @SuppressWarnings("SameParameterValue")
        void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return Parent.class.getSimpleName() + "{" + "name=" + name + "}";
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Parent) {
                var o = (Parent) obj;
                return name != null && name.equals(o.name);
            }
            return false;
        }
    }

    @SuppressWarnings({"WeakerAccess", "unused"})
    private static class Child extends Parent {
        private Integer number;

        public Integer getNumber() {
            return number;
        }

        @SuppressWarnings("SameParameterValue")
        public void setNumber(Integer number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return Child.class.getSimpleName() + "{" + "name=" + getName() + ", number=" + number + "}";
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Child) {
                var o = (Child) obj;
                var nameEquals = getName() != null && getName().equals(o.getName());
                var numberEquals = number != null && number.equals(o.number);
                return nameEquals && numberEquals;
            }
            return false;
        }
    }

}
