package hdfs.cluster.kerberos;

import cluster.HdfsFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.AccessControlException;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

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

    @Test
    void setFileOwner() throws IOException {
        var fs = HdfsFactory.fileSystemSecure();
        var currentUser = UserGroupInformation.getCurrentUser();
        var groups = currentUser.getGroups();

        var hdfsUri = HdfsFactory.getHdfsUri();
        var remotePath = new Path(hdfsUri, "/tmp/owner.txt");

        var os = fs.create(remotePath, true);
        os.writeUTF("hi!");
        os.close();

        var fileStatus1 = fs.getFileStatus(remotePath);
        var owner1 = fileStatus1.getOwner();
        var group1 = fileStatus1.getGroup();
        assertThat(owner1).isEqualTo("client");
        assertThat(group1).isEqualTo("supergroup");

        fs.setOwner(remotePath, "user1", "group1");

        var fileStatus2 = fs.getFileStatus(remotePath);
        assertThat(fileStatus2.getOwner()).isEqualTo("user1");
        assertThat(fileStatus2.getGroup()).isEqualTo("group1");
    }

}
