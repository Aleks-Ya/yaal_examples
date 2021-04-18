package config;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigUtil;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetProperty extends BaseTest {

    @Test
    public void joinPath() {
        var json = "{\"kafka\":{\"common\":{\"bootstrap.servers\":\"kfk.awseuc1.tst.edh.cnb:9093\"}}}";
        var conf = ConfigFactory.parseString(json);
        assertThat(conf.getValue("kafka"), notNullValue());
        assertThat(conf.getValue("kafka.common"), notNullValue());
        var path = ConfigUtil.joinPath("kafka", "common", "bootstrap.servers");
        assertThat(conf.getString(path), equalTo("kfk.awseuc1.tst.edh.cnb:9093"));
    }
}
