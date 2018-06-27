package zookeeper.curator;

import org.apache.curator.test.TestingServer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Use TestingServer to run ZooKeeper.
 */
class TestingServerTest {

    @Test
    @Disabled("NoSuchFieldError: configFileStr")
    void minimal() throws Exception {
        int port = 2199;
        TestingServer server = new TestingServer(port, true);
        String connectString = server.getConnectString();

        Watcher watcher = event -> System.out.println("Watcher!");
        ZooKeeper zk = new ZooKeeper(connectString, 3000, watcher);

        String node = "/my";
        byte[] expData = new byte[]{1, 2, 3};
        zk.create(node, expData, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        byte[] actData = zk.getData(node, true, zk.exists(node, true));
        assertArrayEquals(expData, actData);

        server.stop();
    }
}