package hdfs.docker;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Connect to a HDFS cluster.
 * Run HDFS: docker run -it sequenceiq/hadoop-docker:2.7.1 /etc/bootstrap.sh -bash
 */
class ConnectToDockerHdfs {
    @Test
    void test() throws IOException, URISyntaxException, InterruptedException {
        var conf = new Configuration();
        var hdfsURI = "hdfs://172.17.0.2:9000";
        conf.set("fs.defaultFS", hdfsURI);
        conf.set("dfs.permissions.enabled", "false");
        var user = "root";
        var fs = FileSystem.get(new URI(hdfsURI), conf, user);

        assertTrue(fs instanceof DistributedFileSystem);

        var path = new Path(hdfsURI, "/tmp/my.txt");

        if (fs.exists(path)) {
            fs.delete(path, false);
        }

        var os = fs.create(path);
        var expContent = "hi!";
        os.writeUTF(expContent);
        os.close();

        var is = fs.open(path);
        var actContent = is.readUTF();
        is.close();

        assertEquals(expContent, actContent);
    }
}
