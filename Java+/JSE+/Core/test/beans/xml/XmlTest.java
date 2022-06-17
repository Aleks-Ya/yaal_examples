package beans.xml;

import org.junit.jupiter.api.Test;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class XmlTest {

    @Test
    void codeDecode() {
        Object expObj = "abc";

        var out = new ByteArrayOutputStream();
        try (var encoder = new XMLEncoder(out)) {
            encoder.writeObject(expObj);
        }
        var bytes = out.toByteArray();

        try (var decoder = new XMLDecoder(new ByteArrayInputStream(bytes))) {
            var actObj = decoder.readObject();
            assertThat(actObj).isNotSameAs(expObj).isEqualTo(expObj);
        }
    }

}