package hive.hw3;

import hive.udf.UserAgentUdf;
import org.apache.hadoop.io.Text;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class UserAgentUdfTest {
    private final UserAgentUdf udf = new UserAgentUdf();

    @Test
    public void nullTest() {
        assertNull(udf.evaluate(null));
    }

    @Test
    public void emptyString() {
        ArrayList<Text> res = udf.evaluate(new Text(""));
        assertThat(res, Matchers.contains(new Text("UNKNOWN"), new Text("UNKNOWN"), new Text("")));
    }

    @Test
    public void correct() {
        ArrayList<Text> res = udf.evaluate(new Text("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)"));
        assertThat(res, Matchers.contains(new Text("BOT"), new Text("UNKNOWN"), new Text("")));
    }


}