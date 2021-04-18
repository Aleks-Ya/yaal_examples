package lang.system;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import static java.lang.System.out;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SystemProperties {

    @Test
    public void printAllProperties() {
        out.println("ALL FROM System.getProperties() \n");
        Properties envs = System.getProperties();
        for (Map.Entry<Object, Object> entry : envs.entrySet()) {
            out.printf("%s=%s%n", entry.getKey(), entry.getValue());
        }
    }

    @Test
    public void setProperty() {
        String name = "the_name";
        String value = "the_value";

        assertNull(System.getProperty(name));

        System.setProperty(name, value);

        assertThat(System.getProperty(name), equalTo(value));
    }

    @Test
    public void homeUserDir() {
        out.printf("System.getenv(\"HOME\")           -> %s%n", System.getenv("HOME"));
        out.printf("System.getProperty(\"user.home\") -> %s%n", System.getProperty("user.home"));
    }

    @Test
    public void lineSeparator() {
        String separator = System.getProperty("line.separator");
        assertTrue("\n".equals(separator) || "\r\n".equals(separator));
    }

    @Test
    public void tempDir() {
        String tmpDirStr = System.getProperty("java.io.tmpdir");
        File tmpDir = new File(tmpDirStr);
        System.out.println("Temp dir: " + tmpDir.getAbsolutePath());
    }

    @Test
    public void workingDir() {
        //Linux:
        System.out.printf("System.getenv(\"PWD\")           -> %s%n", System.getenv("PWD"));
        //При запуске из Idea PWD заполняется неправильно

        System.out.printf("System.getProperty(\"user.dir\") -> %s%n", System.getProperty("user.dir"));
    }


}