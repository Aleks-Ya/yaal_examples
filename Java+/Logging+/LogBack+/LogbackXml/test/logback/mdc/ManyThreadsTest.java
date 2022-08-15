package logback.mdc;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Example from https://logback.qos.ch/manual/mdc.html
 */
class ManyThreadsTest {
    private static final Logger log;

    static {
        System.setProperty("logback.configurationFile", "logback/mdc/ManyThreadsTest.xml");
        log = LoggerFactory.getLogger(ManyThreadsTest.class);
    }

    @Test
    void mdc() throws InterruptedException {
        var executor = Executors.newFixedThreadPool(3);
        executor.submit(new ClientHandler("John"));
        executor.submit(new ClientHandler("Mark"));
        executor.submit(new ClientHandler("Mary"));
        executor.shutdown();
        var finishedSuccessfully = executor.awaitTermination(5, TimeUnit.SECONDS);
        assertThat(finishedSuccessfully).isTrue();
    }

    static class ClientHandler implements Runnable {
        private final String clientName;

        ClientHandler(String clientName) {
            this.clientName = clientName;
        }

        @Override
        public void run() {
            MDC.put("client", clientName);
            log.info("Calling to client " + clientName);
            MDC.clear();
        }
    }
}
