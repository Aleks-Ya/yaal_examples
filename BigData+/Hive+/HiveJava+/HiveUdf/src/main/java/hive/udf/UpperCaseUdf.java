package hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UpperCaseUdf extends UDF {
    public Text evaluate(final Text text) {
        if (text == null) {
            return null;
        }
        return new Text(text.toString().toUpperCase());
    }
}
