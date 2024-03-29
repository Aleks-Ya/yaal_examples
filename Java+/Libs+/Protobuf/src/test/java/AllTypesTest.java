import org.junit.Test;
import ru.yaal.protobuf.AllTypes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllTypesTest {
    @Test
    void testName() throws Exception {
        AllTypes.Person.PhoneNumber.Builder phoneNumberBuilder = AllTypes.Person.PhoneNumber.newBuilder();

        phoneNumberBuilder.setNumber("777-99-33");
        AllTypes.Person.PhoneNumber number1 = phoneNumberBuilder.build();

        phoneNumberBuilder.setNumber("+7-921-789-09-66");
        AllTypes.Person.PhoneNumber number2 = phoneNumberBuilder.build();

        AllTypes.Person.Builder builder = AllTypes.Person.newBuilder();
        builder.setBooleanVar(true);
        builder.setDoubleVar(1D);
        builder.setIntegerVar(8);
        builder.addPhone(number1);
        builder.addPhone(number2);
        builder.addIntRepeated(1);
        builder.addIntRepeated(2);

        AllTypes.Person expPerson = builder.build();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        expPerson.writeTo(os);

        InputStream is = new ByteArrayInputStream(os.toByteArray());

        AllTypes.Person actPerson = AllTypes.Person.parseFrom(is);

        assertEquals(actPerson, expPerson);
    }
}
