package docker;

import com.google.protobuf.ServiceException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class DockerPutTest {

    @Test
    @Ignore("Don't work because of Zookeeper in the Docker container contains master IP available only inside of container.")
    public void checkHBaseAvailable() throws IOException, ServiceException {
        Configuration config = HBaseConfiguration.create();
        config.set(HConstants.ZOOKEEPER_QUORUM, "172.17.0.3");
        config.setInt(HConstants.ZOOKEEPER_CLIENT_PORT, 2181);
        HBaseAdmin.checkHBaseAvailable(config);
    }
}