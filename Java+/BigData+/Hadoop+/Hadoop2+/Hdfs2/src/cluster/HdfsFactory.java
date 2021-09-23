package cluster;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hdfs.DistributedFileSystem;

import java.io.IOException;
import java.net.URI;

public class HdfsFactory {
    private static final String HDFS_URI = "hdfs://master-service:8020";

    public static FileSystem initFileSystem() {
        try {
            var conf = new Configuration();
            conf.set("fs.defaultFS", HDFS_URI);
            conf.set("dfs.permissions.enabled", "false");
            var user = "root";
            FileSystem fs = FileSystem.get(URI.create(HDFS_URI), conf, user);
            assert fs instanceof DistributedFileSystem;
            return fs;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getHdfsUri(){
        return HDFS_URI;
    }
}
