package fasterxml.xml.databind.deserialize.subtype_resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Polymorphic Deserialization
 */
class SubtypeResolverTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void parent() throws IOException {
        var expParent = new Parent();
        expParent.setName("my name");
        var parentJson = mapper.writeValueAsString(expParent);
        assertThatJson(parentJson).isEqualTo("{name:'my name'}");
        var actParent = mapper.readValue(parentJson, Parent.class);
        assertThat(actParent).isEqualTo(expParent);
    }

    @Test
    void child() throws IOException {
        var expChild = new Child();
        expChild.setName("my name");
        expChild.setNumber(1);
        var childJson = mapper.writeValueAsString(expChild);
        assertThatJson(childJson).isEqualTo("{name:'my name', number:1}");
        var actChild = mapper.readValue(childJson, Child.class);
        assertThat(actChild).isEqualTo(expChild);
    }

    @Test
    void defaultTyping() throws IOException {
        var ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Parent.class)
                .build();
        mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);

        var expChild = new Child();
        expChild.setName("my name");
        expChild.setNumber(1);
        var childJson = mapper.writeValueAsString(expChild);
        var expJson = "['fasterxml.xml.databind.deserialize.subtype_resolver.SubtypeResolverTest$Child',{name:'my name',number:1}]";
        assertThatJson(childJson).isEqualTo(expJson);
        var actChild = mapper.readValue(childJson, Parent.class);
        assertThat(actChild).isEqualTo(expChild);
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
            if (obj instanceof Parent o) {
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
            if (obj instanceof Child o) {
                var nameEquals = getName() != null && getName().equals(o.getName());
                var numberEquals = number != null && number.equals(o.number);
                return nameEquals && numberEquals;
            }
            return false;
        }
    }

}
