package io.serialization.standard;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Дополнение стандартной сериализации в методах writeObject() и ().
 */
class WriteReadObjectTest {
    @Test
    void standard() throws IOException, ClassNotFoundException {
        var exp = new ForSerialization();
        exp.setNum(8);
        exp.setTransientLong(16);

        var out = new ByteArrayOutputStream();
        var oos = new ObjectOutputStream(out);
        oos.writeObject(exp);
        oos.close();

        var bis = new ByteArrayInputStream(out.toByteArray());
        var ois = new ObjectInputStream(bis);
        var act = (ForSerialization) ois.readObject();

        assertThat(act).isNotSameAs(exp);
        assertThat(act.getNum()).isEqualTo(exp.getNum());
        assertThat(act.getTransientLong()).isEqualTo(33);
    }
}

class ForSerialization implements Serializable {
    private int num = 1;

    private transient long transientLong = 2;

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        transientLong = 33;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getTransientLong() {
        return transientLong;
    }

    public void setTransientLong(long transientLong) {
        this.transientLong = transientLong;
    }
}
