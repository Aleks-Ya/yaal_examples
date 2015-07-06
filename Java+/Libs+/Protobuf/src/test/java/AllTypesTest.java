import org.junit.Test;
import ru.yaal.protobuf.AllTypes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * @author Yablokov Aleksey
 */
public class AllTypesTest {
    @Test
    public void testName() throws Exception {
        AllTypes.Person.Builder builder = AllTypes.Person.newBuilder();
        builder.setBooleanVar(true);
        builder.setDoubleVar(1D);
        builder.setIntegerVar(8);

        AllTypes.Person expPerson = builder.build();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        expPerson.writeTo(os);

        InputStream is = new ByteArrayInputStream(os.toByteArray());

        AllTypes.Person actPerson = AllTypes.Person.parseFrom(is);

        assertEquals(actPerson, expPerson);
    }
}
