package hive.udtf;

import eu.bitwalker.useragentutils.UserAgent;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StandardStructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.javaStringObjectInspector;

public class UserAgentUdtf extends GenericUDTF {
    private static final Logger LOG = LoggerFactory.getLogger(UserAgentUdtf.class);
    static final String browserFieldName = "browser";
    static final String deviceFieldName = "device";
    static final String osFieldName = "os";

    @Override
    public StandardStructObjectInspector initialize(ObjectInspector[] objectInspectors) throws UDFArgumentException {
        LOG.error("Initialize: " + getClass());
        return createObjectInspector();
    }

    @Override
    public void process(Object[] objects) throws HiveException {
        LOG.error("Evaluate: " + Arrays.deepToString(objects));
        if (objects == null) {
            LOG.error("Objects is null. Return.");
            return;
        }
        if (objects.length != 1) {
            throw new IllegalArgumentException("Expected 1 argument, but found " + objects.length);
        }
        String userAgent = PrimitiveObjectInspectorFactory.javaStringObjectInspector.getPrimitiveJavaObject(objects[0]);
        LOG.error("User agent: " + userAgent);

        UserAgent agent = UserAgent.parseUserAgentString(userAgent);
        String browserData = agent.getBrowser().toString();
        String osData = agent.getOperatingSystem().toString();
        String deviceData = agent.getOperatingSystem().getDeviceType().toString();

        forward(new Object[]{browserData, deviceData, osData});
    }

    @Override
    public void close() throws HiveException {

    }

    private StandardStructObjectInspector createObjectInspector() {
        List<String> fieldNames = new ArrayList<>();
        fieldNames.add(browserFieldName);
        fieldNames.add(deviceFieldName);
        fieldNames.add(osFieldName);

        List<ObjectInspector> fieldOIs = Collections.nCopies(3, javaStringObjectInspector);
        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
    }

}
