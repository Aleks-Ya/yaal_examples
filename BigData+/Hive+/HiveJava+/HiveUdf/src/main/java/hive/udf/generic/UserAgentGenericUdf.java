package hive.udf.generic;

import eu.bitwalker.useragentutils.UserAgent;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.lazy.LazyString;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StandardStructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.javaStringObjectInspector;

public class UserAgentGenericUdf extends GenericUDF {
    private static final Logger LOG = LoggerFactory.getLogger(UserAgentGenericUdf.class);
    static final String browserFieldName = "browser";
    static final String browserVersionFieldName = "browser_version";
    static final String osFieldName = "os";

    @Override
    public StandardStructObjectInspector initialize(ObjectInspector[] objectInspectors) throws UDFArgumentException {
        LOG.error("Initialize: " + getClass());
        return createObjectInspector();
    }

    @Override
    public Object evaluate(DeferredObject[] objects) throws HiveException {
        LOG.error("Evaluate: " + Arrays.deepToString(objects));
        if (objects == null) {
            return null;
        }
        if (objects.length != 1) {
            throw new IllegalArgumentException("Expected 1 argument, but found " + objects.length);
        }
        LazyString lazyText = (LazyString) objects[0].get();
        String text = lazyText.getWritableObject().toString();
        if (text == null) {
            return null;
        }

        UserAgent agent = UserAgent.parseUserAgentString(text);
        String browserData = agent.getBrowser().toString();
        String browserVersionData = agent.getBrowserVersion() != null ? agent.getBrowserVersion().toString() : "";
        String osData = agent.getOperatingSystem().toString();

        StandardStructObjectInspector oi = createObjectInspector();

        StructField browserField = oi.getStructFieldRef(browserFieldName);
        StructField browserVersionField = oi.getStructFieldRef(browserVersionFieldName);
        StructField osField = oi.getStructFieldRef(osFieldName);

        Object resObject = oi.create();
        oi.setStructFieldData(resObject, browserField, browserData);
        oi.setStructFieldData(resObject, browserVersionField, browserVersionData);
        oi.setStructFieldData(resObject, osField, osData);

        LOG.error("Evaluated object: " + resObject);
        return resObject;
    }

    private StandardStructObjectInspector createObjectInspector() {
        List<String> fieldNames = new ArrayList<>();
        fieldNames.add(browserFieldName);
        fieldNames.add(browserVersionFieldName);
        fieldNames.add(osFieldName);

        List<ObjectInspector> fieldOIs = Collections.nCopies(3, javaStringObjectInspector);
        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
    }

    @Override
    public String getDisplayString(String[] strings) {
        return "Parse User-Agent header";
    }
}
