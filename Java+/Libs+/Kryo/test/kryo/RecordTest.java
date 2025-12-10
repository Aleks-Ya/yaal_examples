package kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class RecordTest {

    @Test
    void serialize() {
        var kryo = new Kryo();
        kryo.register(SomeClass.class);

        var exp = new SomeClass("Hello Kryo!");

        var os = new ByteArrayOutputStream();
        var output = new Output(os);
        kryo.writeObject(output, exp);
        output.close();

        var input = new Input(new ByteArrayInputStream(os.toByteArray()));
        var act = kryo.readObject(input, SomeClass.class);
        input.close();

        assertThat(act).isEqualTo(exp);
    }

    record SomeClass(String value) {
    }
}