package hdfs.cluster.kerberos;

import cluster.HdfsFactory;
import org.apache.hadoop.fs.Path;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static cluster.Principal.CLIENT;
import static org.assertj.core.api.Assertions.assertThat;

class ConnectToHdfsKerberosCluster {
    @Test
    void connect() throws IOException {
        var conf = HdfsFactory.configurationSecure(CLIENT);
        var fs = HdfsFactory.fileSystemSecure(conf);

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
