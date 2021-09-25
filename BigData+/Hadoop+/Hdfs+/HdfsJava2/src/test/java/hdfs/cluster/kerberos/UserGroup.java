package hdfs.cluster.kerberos;

import cluster.HdfsFactory;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class UserGroup {

    /**
     * Prepare: create Linux user and group:
     * sudo groupadd superuser
     * sudo useradd -g superuser client
     */
    @Test
    void getCurrentUser() throws IOException {
        HdfsFactory.fileSystemSecure();

        var currentUser = UserGroupInformation.getCurrentUser();
        var userName = currentUser.getUserName();
        var shortUserName = currentUser.getShortUserName();
        var groups = currentUser.getGroups();

        assertThat(userName).isEqualTo("client@HADOOPCLUSTER.LOCAL");
        assertThat(shortUserName).isEqualTo("client");
        assertThat(groups).contains("superuser");
    }

}
