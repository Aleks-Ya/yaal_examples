package beans.xml;

import org.junit.jupiter.api.Test;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

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
            assertThat(actObj, not(sameInstance(expObj)));
            assertThat(actObj, equalTo(expObj));
        }
    }

}