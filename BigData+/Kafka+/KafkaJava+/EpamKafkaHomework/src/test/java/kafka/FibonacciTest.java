package kafka;

import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.server.KafkaConfig;
import kafka.server.KafkaServer;
import kafka.utils.TestUtils;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;
import kafka.zk.EmbeddedZookeeper;
import org.I0Itec.zkclient.ZkClient;
import org.apache.kafka.common.utils.MockTime;
import org.apache.kafka.common.utils.Time;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

/**
 * @author Aleksey Yablokov
 */
public class FibonacciTest {
    private static final Logger log = LoggerFactory.getLogger(FibonacciTest.class);
    private static final String brokerHost = "127.0.0.1";
    private static final int brokerPort = 9095;
    private static final String topic = "fibonacci";
    private KafkaServer kafkaServer;
    private ZkClient zkClient;
    private EmbeddedZookeeper zkServer;

    @Before
    public void setUp() throws IOException {
        String zkHost = "127.0.0.1";
        zkServer = new EmbeddedZookeeper();
        String zkConnect = zkHost + ":" + zkServer.port();
        zkClient = new ZkClient(zkConnect, 30000, 30000, ZKStringSerializer$.MODULE$);
        ZkUtils zkUtils = ZkUtils.apply(zkClient, false);

        Properties brokerProps = new Properties();
        brokerProps.setProperty("zookeeper.connect", zkConnect);
        brokerProps.setProperty("broker.id", "0");
        brokerProps.setProperty("log.dirs", Files.createTempDirectory("kafka-").toAbsolutePath().toString());
        brokerProps.setProperty("listeners", "PLAINTEXT://" + brokerHost + ":" + brokerPort);
        KafkaConfig config = new KafkaConfig(brokerProps);
        Time mock = new MockTime();
        kafkaServer = TestUtils.createServer(config, mock);
        AdminUtils.createTopic(zkUtils, topic, 1, 1, new Properties(), RackAwareMode.Disabled$.MODULE$);
    }

    @Test(timeout = 30_000)
    public void fibonacci() throws ExecutionException, InterruptedException {
        int sendNumberCount = 8;
        FibonacciProducer producer = new FibonacciProducer(topic, sendNumberCount, brokerHost, brokerPort);
        producer.work();

        FibonacciConsumer consumer = new FibonacciConsumer(topic, 2, brokerHost, brokerPort);
        int receiveNumberCount = 4;
        List<Long> fibonacciSums = new ArrayList<>();
        consumer.work(sum -> {
            fibonacciSums.add(sum);
            if (fibonacciSums.size() == receiveNumberCount) {
                consumer.stop();
            }
        });
        log.info("Received numbers: " + fibonacciSums);

        assertThat(fibonacciSums, contains(1L, 4L, 12L, 33L));
    }

    @After
    public void tearDown() {
        kafkaServer.shutdown();
        zkClient.close();
        zkServer.shutdown();
    }
}