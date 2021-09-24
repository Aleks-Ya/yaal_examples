package hdfs.cluster.kerberos;

import cluster.HdfsFactory;
import org.apache.hadoop.fs.Path;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Connect to a HDFS cluster with Kerberos.
 * 1. Run Hadoop cluster ("Building+/Docker+/DockerImage+/Application+/Hadoop+/Hadoop2+/HdfsKerberosCluster")
 * 2. Copy keytab: "docker cp hdfs-master:/tmp/kerberos/client.keytab /tmp/client.keytab"
 * 3. Copy krb5.conf: "docker cp hdfs-master:/tmp/kerberos/krb5.conf /tmp/krb5.conf"
 * 4. Copy core-site.xml: "docker cp hdfs-master:/opt/hadoop/etc/hadoop/core-site.xml /tmp/core-site.xml"
 * 5. Copy hdfs-site.xml: "docker cp hdfs-master:/opt/hadoop/etc/hadoop/hdfs-site.xml /tmp/hdfs-site.xml"
 */
class ConnectToHdfsKerberosCluster {
    @Test
    void connect() throws IOException {
        var fs = HdfsFactory.fileSystemSecure();

        var path = new Path("/tmp/my.txt");

        if (fs.exists(path)) {
            fs.delete(path, false);
        }

        var expContent = "hi!";
        try (var os = fs.create(path)) {
            os.writeUTF(expContent);
        }

        String actContent;
        try (var is = fs.open(path)) {
            actContent = is.readUTF();
        }
        assertThat(actContent).isEqualTo(expContent);

    }
}
