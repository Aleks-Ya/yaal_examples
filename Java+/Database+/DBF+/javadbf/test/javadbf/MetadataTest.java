package javadbf;

import com.linuxense.javadbf.DBFReader;
import org.junit.jupiter.api.Test;

class MetadataTest {

    @Test
    void dbfReader() {
        var is = getClass().getClassLoader().getResourceAsStream("PI_316201712.DBF");
        var reader = new DBFReader(is);
        var fieldsCount = reader.getFieldCount();
        System.out.println("FieldsCount: " + fieldsCount);
        for (var f = 0; f < fieldsCount; f++) {
            var field = reader.getField(f);
            var fieldName = field.getName();
            System.out.println("Field name: " + fieldName);
        }
    }
}