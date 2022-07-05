package dbf;

import org.jamel.dbf.processor.DbfProcessor;
import org.jamel.dbf.structure.DbfHeader;
import org.junit.jupiter.api.Test;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;

import static java.util.Objects.requireNonNull;

class MetadataTest {

    @Test
    void dbfInfo() {
        var dbf = new File(getClass().getClassLoader().getResource("PI_316201712.DBF").getFile());
        var info = DbfProcessor.readDbfInfo(dbf);
        System.out.println(info);
    }

    @Test
    void dbfHeader() {
        var is = requireNonNull(getClass().getClassLoader().getResourceAsStream("PI_316201712.DBF"));
        DataInput di = new DataInputStream(is);
        var header = DbfHeader.read(di);
        var day = header.getDay();
        var fieldsCount = header.getFieldsCount();
        System.out.println("Day: " + day);
        System.out.println("FieldsCount: " + fieldsCount);
        for (var f = 0; f < fieldsCount; f++) {
            var field = header.getField(f);
            var fieldName = field.getName();
            System.out.println("Field name: " + fieldName);
        }
    }
}