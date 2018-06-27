package zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Use ZooKeeperServerMain to run ZooKeeper (without Curator).
 */
class ServerTest {

    @Test
    void minimal() throws IOException, KeeperException, InterruptedException {
        Path dir = Files.createTempDirectory("ZooKeeper_");
        ZooKeeperServerMain zooKeeperServer = new ZooKeeperServerMain();
        final ServerConfig configuration = new ServerConfig();
        String port = "2199";
        configuration.parse(new String[]{port, dir.toString()});

        new Thread(() -> {
            try {
                zooKeeperServer.runFromConfig(configuration);
            } catch (IOException e) {
                System.err.println(("ZooKeeper Failed" + e));
            }
        }).start();
        Thread.sleep(500);

        Watcher watcher = event -> System.out.println("Watcher!");
        ZooKeeper zk = new ZooKeeper("localhost:" + port, 3000, watcher);

        String node = "/my";
        byte[] expData = new byte[]{1, 2, 3};
        zk.create(node, expData, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        byte[] actData = zk.getData(node, true, zk.exists(node, true));
        assertArrayEquals(expData, actData);
    }

    @Test
    void withQuorum() throws IOException {
        Path dir = Files.createTempDirectory("ZooKeeper_");

        Properties startupProperties = new Properties();
        startupProperties.setProperty("dataDir", dir.toString());
        startupProperties.setProperty("clientPort", "21818");

        QuorumPeerConfig quorumConfiguration = new QuorumPeerConfig();
        try {
            quorumConfiguration.parseProperties(startupProperties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ZooKeeperServerMain zooKeeperServer = new ZooKeeperServerMain();
        final ServerConfig configuration = new ServerConfig();
        configuration.readFrom(quorumConfiguration);

        new Thread(() -> {
            try {
                zooKeeperServer.runFromConfig(configuration);
            } catch (IOException e) {
                System.err.println(("ZooKeeper Failed" + e));
            }
        }).start();
    }
}