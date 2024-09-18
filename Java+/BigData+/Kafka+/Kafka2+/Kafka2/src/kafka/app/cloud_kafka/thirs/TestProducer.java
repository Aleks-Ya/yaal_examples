package kafka.app.cloud_kafka.thirs;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class TestProducer {
    public static void main(String[] args) throws IOException {
        try {
            var caWriter = new PrintWriter("/tmp/ca.pem", StandardCharsets.UTF_8);
            var ca = System.getenv("CLOUDKARAFKA_CA");
            caWriter.println(ca);
            caWriter.close();

            var certWriter = new PrintWriter("/tmp/cert.pem", StandardCharsets.UTF_8);
            var cert = System.getenv("CLOUDKARAFKA_CERT");
            certWriter.println(cert);
            certWriter.close();

            var keyWriter = new PrintWriter("/tmp/key.pem", StandardCharsets.UTF_8);
            var privateKey = System.getenv("CLOUDKARAFKA_PRIVATE_KEY");
            keyWriter.println(privateKey);
            keyWriter.close();

            var r = Runtime.getRuntime();
            var p = r.exec("openssl pkcs12 -export -password pass:test1234 -out /tmp/store.pkcs12 -inkey /tmp/key.pem -certfile /tmp/ca.pem -in /tmp/cert.pem -caname 'CA Root' -name client");
            p.waitFor();

            p = r.exec("keytool -importkeystore -noprompt -srckeystore /tmp/store.pkcs12 -destkeystore /tmp/keystore.jks -srcstoretype pkcs12 -srcstorepass test1234 -srckeypass test1234 -destkeypass test1234 -deststorepass test1234 -alias client");
            p.waitFor();

            p = r.exec("keytool -noprompt -keystore /tmp/truststore.jks -alias CARoot -import -file /tmp/ca.pem -storepass test1234");
            p.waitFor();

            var brokers = System.getenv("CLOUDKARAFKA_BROKERS");
            var topicPrefix = System.getenv("CLOUDKARAFKA_TOPIC_PREFIX");

            var props = new Properties();
            props.put("bootstrap.servers", brokers);
            props.put("group.id", "test");
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("session.timeout.ms", "30000");
            props.put("key.serializer", org.apache.kafka.common.serialization.StringSerializer.class.getName());
            props.put("value.serializer", org.apache.kafka.common.serialization.StringSerializer.class.getName());
            props.put("security.protocol", "SSL");
            props.put("ssl.truststore.location", "/tmp/truststore.jks");
            props.put("ssl.truststore.password", "test1234");
            props.put("ssl.keystore.location", "/tmp/keystore.jks");
            props.put("ssl.keystore.password", "test1234");
            props.put("ssl.keypassword", "test1234");

            Producer<String, String> producer = new KafkaProducer<>(props);
            for (var i = 0; i < 100; i++)
                producer.send(new ProducerRecord<>(topicPrefix + "default", Integer.toString(i), Integer.toString(i)));

            producer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException | InterruptedException e) {
            System.out.println(e);
        }
    }
}
