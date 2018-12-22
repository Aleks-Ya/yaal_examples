package hdfs.docker;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Connect to a HDFS cluster.
 * Run HDFS: docker run -it sequenceiq/hadoop-docker:2.7.1 /etc/bootstrap.sh -bash
 */
public class ConnectToDockerHdfsTest {
    @Test
    public void test() throws IOException, URISyntaxException, InterruptedException {

        Configuration conf = new Configuration();
        String hdfsURI = "hdfs://172.17.0.2:9000";
        conf.set("fs.defaultFS", hdfsURI);
        conf.set("dfs.permissions.enabled", "false");
        String user = "root";
        FileSystem fs = FileSystem.get(new URI(hdfsURI), conf, user);

        assertTrue(fs instanceof DistributedFileSystem);

        Path path = new Path(hdfsURI, "/tmp/my.txt");

        if (fs.exists(path)) {
            fs.delete(path, false);
        }

        FSDataOutputStream os = fs.create(path);
        String expContent = "hi!";
        os.writeUTF(expContent);
        os.close();

        FSDataInputStream is = fs.open(path);
        String actContent = is.readUTF();
        is.close();

        assertEquals(expContent, actContent);


    }
}
