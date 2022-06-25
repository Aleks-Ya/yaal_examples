package lang.string.string.split;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class SplitTest {

    @Test
    void splitByDot() {
        var s = "config.server.host";
        var words = s.split("\\.");
        assertThat(words).containsExactly("config", "server", "host");
    }

    @Test
    void splitLimit() {
        var s = "config.infra.server.host";

        var splitNoLimit = s.split("\\.");
        assertThat(splitNoLimit).containsExactly("config", "infra", "server", "host");

        var splitLimit0 = s.split("\\.", 0);
        assertThat(splitLimit0).containsExactly("config", "infra", "server", "host");

        var splitLimit1 = s.split("\\.", 1);
        assertThat(splitLimit1).containsExactly("config.infra.server.host");

        var splitLimit2 = s.split("\\.", 2);
        assertThat(splitLimit2).containsExactly("config", "infra.server.host");

        var splitLimit3 = s.split("\\.", 3);
        assertThat(splitLimit3).containsExactly("config", "infra", "server.host");

        var splitLimit4 = s.split("\\.", 4);
        assertThat(splitLimit4).containsExactly("config", "infra", "server", "host");

        var splitLimit5 = s.split("\\.", 5);
        assertThat(splitLimit5).containsExactly("config", "infra", "server", "host");
    }


}