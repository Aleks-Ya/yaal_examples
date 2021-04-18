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

public class XmlTest {

    @Test
    public void codeDecode() {
        Object expObj = "abc";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (XMLEncoder encoder = new XMLEncoder(out)) {
            encoder.writeObject(expObj);
        }
        byte[] bytes = out.toByteArray();

        try (XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(bytes))) {
            Object actObj = decoder.readObject();
            assertThat(actObj, not(sameInstance(expObj)));
            assertThat(actObj, equalTo(expObj));
        }
    }

}