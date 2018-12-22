package dbf;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import org.junit.Test;

import java.io.InputStream;

public class Metadata {

    @Test
    public void dbfReader() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("PI_316201712.DBF");
        DBFReader reader = new DBFReader(is);
        int fieldsCount = reader.getFieldCount();
        System.out.println("FieldsCount: " + fieldsCount);
        for (int f = 0; f < fieldsCount; f++) {
            DBFField field = reader.getField(f);
            String fieldName = field.getName();
            System.out.println("Field name: " + fieldName);
        }
    }
}