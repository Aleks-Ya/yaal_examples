package config;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GetPropertyTest extends BaseTest {

    @Test
    void joinPath() {
        var json = "{\"kafka\":{\"common\":{\"bootstrap.servers\":\"kfk.awseuc1.tst.edh.cnb:9093\"}}}";
        var conf = ConfigFactory.parseString(json);
        assertThat(conf.getValue("kafka")).isNotNull();
        assertThat(conf.getValue("kafka.common")).isNotNull();
        var path = ConfigUtil.joinPath("kafka", "common", "bootstrap.servers");
        assertThat(conf.getString(path)).isEqualTo("kfk.awseuc1.tst.edh.cnb:9093");
    }
}
