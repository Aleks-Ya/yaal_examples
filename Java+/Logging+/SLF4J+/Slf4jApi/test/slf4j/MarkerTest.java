package slf4j;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class MarkerTest {
    private static final Logger log = LoggerFactory.getLogger(MarkerTest.class);

    @Test
    void marker() {
        var marker = MarkerFactory.getMarker("JOHN");
        var marker2 = MarkerFactory.getMarker("MARY");
        log.atInfo().setMessage("my message").addMarker(marker).addMarker(marker2).log();
    }

}