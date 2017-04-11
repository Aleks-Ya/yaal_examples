package hive;

import org.apache.hadoop.hive.serde2.lazy.ByteArrayRef;
import org.apache.hadoop.hive.serde2.lazy.LazyString;
import org.apache.hadoop.hive.serde2.lazy.objectinspector.primitive.LazyPrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.lazy.objectinspector.primitive.LazyStringObjectInspector;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LazyStringTest {
    @Test
    public void init() {
        String str = "abc";

        LazyStringObjectInspector oi = LazyPrimitiveObjectInspectorFactory.getLazyStringObjectInspector(false, (byte) 0);
        LazyString lazyString = new LazyString(oi);

        ByteArrayRef byteArrayRef = new ByteArrayRef();
        byteArrayRef.setData(str.getBytes());

        lazyString.init(byteArrayRef, 0, byteArrayRef.getData().length);

        assertThat(lazyString.getWritableObject().toString(), equalTo(str));
    }
}
