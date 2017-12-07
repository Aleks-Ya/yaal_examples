package databind.deserialize.subtype_resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Polymorphic Deserialization
 */
public class SubtypeResolverTest {
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void parent() throws IOException {
        Parent expParent = new Parent();
        expParent.setName("my name");
        String parentJson = mapper.writeValueAsString(expParent);
        assertEquals("{\"name\":\"my name\"}", parentJson);
        Parent actParent = mapper.readValue(parentJson, Parent.class);
        assertEquals(expParent, actParent);
    }

    @Test
    public void child() throws IOException {
        Child expChild = new Child();
        expChild.setName("my name");
        expChild.setNumber(1);
        String childJson = mapper.writeValueAsString(expChild);
        assertEquals("{\"name\":\"my name\",\"number\":1}", childJson);
        Child actChild = mapper.readValue(childJson, Child.class);
        assertEquals(expChild, actChild);
    }

    @Test
    public void defaultTyping() throws IOException, JSONException {
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        Child expChild = new Child();
        expChild.setName("my name");
        expChild.setNumber(1);
        String childJson = mapper.writeValueAsString(expChild);
        String expJson = "[databind.deserialize.subtype_resolver.SubtypeResolverTest$Child,{name:'my name',number:1}]";
        JSONAssert.assertEquals(expJson, childJson, JSONCompareMode.STRICT);
        Parent actChild = mapper.readValue(childJson, Parent.class);
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
                Parent o = (Parent) obj;
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
                Child o = (Child) obj;
                boolean nameEquals = getName() != null && getName().equals(o.getName());
                boolean numberEquals = number != null && number.equals(o.number);
                return nameEquals && numberEquals;
            }
            return false;
        }
    }

}
