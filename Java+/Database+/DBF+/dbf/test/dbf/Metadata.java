package dbf;

import org.jamel.dbf.processor.DbfProcessor;
import org.jamel.dbf.structure.DbfField;
import org.jamel.dbf.structure.DbfHeader;
import org.junit.jupiter.api.Test;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.InputStream;

public class Metadata {

    @Test
    void dbfInfo() {
        File dbf = new File(getClass().getClassLoader().getResource("PI_316201712.DBF").getFile());
        String info = DbfProcessor.readDbfInfo(dbf);
        System.out.println(info);
    }

    @Test
    void dbfHeader() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("PI_316201712.DBF");
        DataInput di = new DataInputStream(is);
        DbfHeader header = DbfHeader.read(di);
        byte day = header.getDay();
        int fieldsCount = header.getFieldsCount();
        System.out.println("Day: " + day);
        System.out.println("FieldsCount: " + fieldsCount);
        for (int f = 0; f < fieldsCount; f++) {
            DbfField field = header.getField(f);
            String fieldName = field.getName();
            System.out.println("Field name: " + fieldName);
        }
    }
}