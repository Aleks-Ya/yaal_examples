package cluster;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;

public class HdfsFactory {
    private static final String HDFS_URI = "hdfs://hdfs-master.hdfs.yaal.ru:9000";

    public static FileSystem initFileSystem() {
        try {
            var conf = new Configuration();
            conf.set("fs.defaultFS", HDFS_URI);
            conf.set("dfs.permissions.enabled", "false");
            var user = "root";
            var fs = FileSystem.get(URI.create(HDFS_URI), conf, user);
            assert fs instanceof DistributedFileSystem;
            return fs;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getHdfsUri() {
        return HDFS_URI;
    }

    public static Configuration configurationSecure() {
        try {
            System.setProperty("java.security.krb5.conf", "/tmp/krb5.conf");

            var conf = new Configuration();
            conf.addResource(new FileInputStream("/tmp/core-site.xml"));
            conf.addResource(new FileInputStream("/tmp/hdfs-site.xml"));
            conf.reloadConfiguration();

            UserGroupInformation.setConfiguration(conf);

            UserGroupInformation.loginUserFromKeytab("client@HADOOPCLUSTER.LOCAL", "/tmp/client.keytab");
            return conf;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static FileSystem fileSystemSecure() {
        try {
            var conf = HdfsFactory.configurationSecure();
            var fs = FileSystem.get(conf);
            assert fs instanceof DistributedFileSystem;
            return fs;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
