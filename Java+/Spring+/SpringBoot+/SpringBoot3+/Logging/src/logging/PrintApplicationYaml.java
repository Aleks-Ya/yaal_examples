package logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.requireNonNull;

@Component
@SuppressWarnings("unused")
public class PrintApplicationYaml {
    private final Logger logger = LoggerFactory.getLogger(PrintApplicationYaml.class);

    @PostConstruct
    public void print() throws IOException {
        var resource = "application.yaml";
        var is = requireNonNull(getClass().getClassLoader().getResourceAsStream(resource));
        var content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        logger.info("Content of '{}':\n{}", resource, content);
    }
}
