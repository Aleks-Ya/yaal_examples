package mockito.core.create;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

class SerializableMockTest {
    @Test
    void createSerializableMock() throws IOException, ClassNotFoundException {
        MyClass mock = mock(MyClass.class, withSettings().serializable());
        String text = "xyz";
        when(mock.getText()).thenReturn(text);
        assertThat(mock.getText()).isEqualTo(text);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(mock);

        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(is);
        MyClass deserializedMock = (MyClass) ois.readObject();

        assertThat(deserializedMock.getText()).isEqualTo(text);
    }
}

class MyClass implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public String getText() {
        return "abc";
    }
}