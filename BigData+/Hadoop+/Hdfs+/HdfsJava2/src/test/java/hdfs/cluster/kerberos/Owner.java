package hdfs.cluster.kerberos;

import cluster.HdfsFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.AccessControlException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class Owner {

    @Test
    void getFileOwner() throws IOException {
        var fs = HdfsFactory.fileSystemSecure();

        var hdfsUri = HdfsFactory.getHdfsUri();
        var remotePath = new Path(hdfsUri, "/tmp/owner.txt");

        var os = fs.create(remotePath, true);
        os.writeUTF("hi!");
        os.close();

        var status = fs.getFileStatus(remotePath);
        var owner = status.getOwner();
        assertThat(owner).isEqualTo("client");
    }

    @Test
    void setFileOwner_AccessControlException() throws IOException {
        var fs = HdfsFactory.fileSystemSecure();

        var hdfsUri = HdfsFactory.getHdfsUri();
        var remotePath = new Path(hdfsUri, "/tmp/owner.txt");

        var os = fs.create(remotePath, true);
        os.writeUTF("hi!");
        os.close();

        assertThatThrownBy(() -> fs.setOwner(remotePath, "user1", "group1"))
                .isInstanceOf(AccessControlException.class)
                .hasMessageContaining("User client is not a super user (non-super user cannot change owner).");
    }

}
