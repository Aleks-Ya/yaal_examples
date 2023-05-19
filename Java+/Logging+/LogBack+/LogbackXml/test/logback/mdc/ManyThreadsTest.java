package logback.mdc;

import logback.BaseLogbackTest;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Example from <a href="https://logback.qos.ch/manual/mdc.html">manual</a>
 */
class ManyThreadsTest extends BaseLogbackTest {
    @Test
    void mdc() throws InterruptedException {
        try (var stdOut = reinitialize("logback/mdc/ManyThreadsTest.xml")) {
            var executor = Executors.newFixedThreadPool(3);
            executor.submit(new ClientHandler("John"));
            executor.submit(new ClientHandler("Mark"));
            executor.submit(new ClientHandler("Mary"));
            executor.shutdown();
            var finishedSuccessfully = executor.awaitTermination(5, TimeUnit.SECONDS);
            assertThat(finishedSuccessfully).isTrue();
            assertThat(stdOut.toString()).contains(
                    "Mark - Calling to client Mark",
                    "John - Calling to client John",
                    "Mary - Calling to client Mary"
            );
        }
    }

    static class ClientHandler implements Runnable {
        private final String clientName;

        ClientHandler(String clientName) {
            this.clientName = clientName;
        }

        @Override
        public void run() {
            var log = LoggerFactory.getLogger(ClientHandler.class);
            MDC.put("client", clientName);
            log.info("Calling to client " + clientName);
            MDC.clear();
        }
    }
}
