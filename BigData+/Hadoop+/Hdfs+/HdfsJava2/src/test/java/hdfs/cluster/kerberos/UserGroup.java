package hdfs.cluster.kerberos;

import cluster.HdfsFactory;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static cluster.Principal.CLIENT;
import static cluster.Principal.CLIENT_SUPER;
import static org.assertj.core.api.Assertions.assertThat;


class UserGroup {

    @Test
    void getCurrentUser_client() throws IOException {
        HdfsFactory.configurationSecure(CLIENT);
        var currentUser = UserGroupInformation.getCurrentUser();
        assertThat(currentUser.getUserName()).isEqualTo("client@HADOOPCLUSTER.LOCAL");
        assertThat(currentUser.getShortUserName()).isEqualTo("client");
        assertThat(currentUser.getGroups()).isEmpty();
    }

    @Test
    void getCurrentUser_client_super() throws IOException {
        HdfsFactory.configurationSecure(CLIENT_SUPER);
        var currentUser = UserGroupInformation.getCurrentUser();
        assertThat(currentUser.getUserName()).isEqualTo(CLIENT_SUPER.getPrincipal());
        assertThat(currentUser.getShortUserName()).isEqualTo("client_super");
        assertThat(currentUser.getGroups()).isEmpty();
    }

}
