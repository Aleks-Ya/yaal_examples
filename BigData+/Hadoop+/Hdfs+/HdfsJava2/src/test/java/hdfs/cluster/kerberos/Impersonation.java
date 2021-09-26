package hdfs.cluster.kerberos;

import cluster.HdfsFactory;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;

import static cluster.Principal.CLIENT_SUPER;
import static org.assertj.core.api.Assertions.assertThat;


class Impersonation {

    @Test
    void doAs() throws IOException, InterruptedException {
        var conf = HdfsFactory.configurationSecure(CLIENT_SUPER);
        var hdfsUri = HdfsFactory.getHdfsUri();
        var remotePath = new Path(hdfsUri, "/tmp/owner.txt");

        var proxyUser = "joe";
        var currentUgi = UserGroupInformation.getLoginUser();
        assertThat(currentUgi.getUserName()).isEqualTo(CLIENT_SUPER.getPrincipal());

        var proxyUgi = UserGroupInformation.createProxyUser(proxyUser, currentUgi);
        proxyUgi.doAs((PrivilegedExceptionAction<Void>) () -> {
            var fsJoe = FileSystem.get(conf);
            var os = fsJoe.create(remotePath, true);
            os.writeUTF("hi!");
            os.close();
            return null;
        });

        var fs = HdfsFactory.fileSystemSecure(conf);
        var fileStatus = fs.getFileStatus(remotePath);
        assertThat(fileStatus.getOwner()).isEqualTo(proxyUser);
        assertThat(fileStatus.getGroup()).contains("supergroup");
    }

}
