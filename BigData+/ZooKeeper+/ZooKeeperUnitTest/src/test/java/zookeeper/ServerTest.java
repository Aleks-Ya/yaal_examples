package zookeeper;

import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

class ServerTest {

    @Test
    void minimal() {
        File dir = new File(System.getProperty("java.io.tmpdir"), "zookeeper").getAbsoluteFile();
        ZooKeeperServerMain zooKeeperServer = new ZooKeeperServerMain();
        final ServerConfig configuration = new ServerConfig();
        configuration.parse(new String[]{"2199", dir.getAbsolutePath()});

        new Thread(() -> {
            try {
                zooKeeperServer.runFromConfig(configuration);
            } catch (IOException e) {
                System.err.println(("ZooKeeper Failed" + e));
            }
        }).start();
    }

    @Test
    void withQuorum() {
        File dir = new File(System.getProperty("java.io.tmpdir"), "zookeeper").getAbsoluteFile();

        Properties startupProperties = new Properties();
        startupProperties.setProperty("dataDir", dir.getAbsolutePath());
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