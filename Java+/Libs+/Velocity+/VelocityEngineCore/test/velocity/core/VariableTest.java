package velocity.core;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;

class VariableTest {
    @Test
    void variable() {
        var velocityEngine = new VelocityEngine();
        velocityEngine.init();

        var context = new VelocityContext();
        context.put("name", "World");

        var template = "Hello $name!";

        var writer = new StringWriter();
        velocityEngine.evaluate(context, writer, "HelloWorld", template);

        assertThat(writer.toString()).isEqualTo("Hello World!");
    }

    @Test
    void timestamp() {
        var velocityEngine = new VelocityEngine();
        velocityEngine.init();

        var context = new VelocityContext();
        var timestamp = System.currentTimeMillis();
        context.put("timestamp", timestamp);

        var template = "Current timestamp: $timestamp";

        var writer = new StringWriter();
        velocityEngine.evaluate(context, writer, "TimestampTest", template);

        assertThat(writer.toString()).isEqualTo("Current timestamp: " + timestamp);
    }
}
