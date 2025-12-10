package kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class ClassTest {

    @Test
    void serialize() {
        var kryo = new Kryo();
        kryo.register(SomeClass.class);

        var exp = new SomeClass();
        exp.value = "Hello Kryo!";

        var os = new ByteArrayOutputStream();
        var output = new Output(os);
        kryo.writeObject(output, exp);
        output.close();

        var input = new Input(new ByteArrayInputStream(os.toByteArray()));
        var act = kryo.readObject(input, SomeClass.class);
        input.close();

        assertThat(act).isEqualTo(exp);
    }

    static public class SomeClass {
        String value;

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            var someClass = (SomeClass) o;
            return Objects.equals(value, someClass.value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }
    }
}