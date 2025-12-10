package kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class DifferentKryoInstancesTest {

    @Test
    void serialize() throws FileNotFoundException {
        var exp = new SomeClass("Hello Kryo!");
        var file = FileUtil.createAbsentTempFile();

        var kryo1 = new Kryo();
        kryo1.register(SomeClass.class);
        var os = new FileOutputStream(file);
        var output = new Output(os);
        kryo1.writeObject(output, exp);
        output.close();

        var kryo2 = new Kryo();
        kryo2.register(SomeClass.class);
        var input = new Input(new FileInputStream(file));
        var act = kryo2.readObject(input, SomeClass.class);
        input.close();

        assertThat(act).isEqualTo(exp);
    }

    record SomeClass(String value) {
    }
}