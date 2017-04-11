package hive.udf.generic;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.lazy.ByteArrayRef;
import org.apache.hadoop.hive.serde2.lazy.LazyString;
import org.apache.hadoop.hive.serde2.lazy.objectinspector.primitive.LazyPrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.lazy.objectinspector.primitive.LazyStringObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StandardStructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class UserAgentGenericUdfTest {

    @Test
    public void bot() throws HiveException {
        testUserAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)",
                "BOT", "UNKNOWN", "");
    }

    @Test
    public void firefox() throws HiveException {
        testUserAgent("Mozilla/5.0 (Windows NT 5.1; rv:24.0) Gecko/20100101 Firefox/24.0",
                "FIREFOX24", "WINDOWS_XP", "24.0");
    }

    @Test
    public void chrome() throws HiveException {
        testUserAgent(" Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1 ",
                "CHROME21", "WINDOWS_XP", "21.0.1180.89");
    }

    private void testUserAgent(String userAgent, String expBrowser, String expOs, String expBrowserVersion) throws HiveException {
        UserAgentGenericUdf agent2 = new UserAgentGenericUdf();
        StandardStructObjectInspector resultOI = agent2.initialize(
                new ObjectInspector[]{PrimitiveObjectInspectorFactory.javaStringObjectInspector});

        LazyStringObjectInspector argIO = LazyPrimitiveObjectInspectorFactory.getLazyStringObjectInspector(false, (byte) 0);

        ByteArrayRef byteArrayRef = new ByteArrayRef();
        byteArrayRef.setData(userAgent.getBytes());

        LazyString lazyUserAgent = new LazyString(argIO);
        lazyUserAgent.init(byteArrayRef, 0, byteArrayRef.getData().length);

        GenericUDF.DeferredJavaObject object = new GenericUDF.DeferredJavaObject(lazyUserAgent);

        Object result = agent2.evaluate(new GenericUDF.DeferredObject[]{object});

        StructField browserField = resultOI.getStructFieldRef(UserAgentGenericUdf.browserFieldName);
        StructField browserVersionField = resultOI.getStructFieldRef(UserAgentGenericUdf.browserVersionFieldName);
        StructField osField = resultOI.getStructFieldRef(UserAgentGenericUdf.osFieldName);

        String browserFieldData = (String) resultOI.getStructFieldData(result, browserField);
        String browserVersionFieldData = (String) resultOI.getStructFieldData(result, browserVersionField);
        String osFieldData = (String) resultOI.getStructFieldData(result, osField);

        assertThat(browserFieldData, equalTo(expBrowser));
        assertThat(browserVersionFieldData, equalTo(expBrowserVersion));
        assertThat(osFieldData, equalTo(expOs));
    }
}