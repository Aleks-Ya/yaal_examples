package lang.system;

import org.junit.jupiter.api.Test;

import java.io.File;

import static java.lang.System.out;
import static org.assertj.core.api.Assertions.assertThat;

class SystemPropertiesTest {

    @Test
    void printAllProperties() {
        out.println("ALL FROM System.getProperties() \n");
        var envs = System.getProperties();
        for (var entry : envs.entrySet()) {
            out.printf("%s=%s%n", entry.getKey(), entry.getValue());
        }
    }

    @Test
    void setProperty() {
        var name = "the_name";
        var value = "the_value";

        assertThat(System.getProperty(name)).isNull();

        System.setProperty(name, value);

        assertThat(System.getProperty(name)).isEqualTo(value);
    }

    @Test
    void homeUserDir() {
        out.printf("System.getenv(\"HOME\")           -> %s%n", System.getenv("HOME"));
        out.printf("System.getProperty(\"user.home\") -> %s%n", System.getProperty("user.home"));
    }

    @Test
    void lineSeparator() {
        var separator = System.getProperty("line.separator");
        assertThat("\n".equals(separator) || "\r\n".equals(separator)).isTrue();
    }

    @Test
    void tempDir() {
        var tmpDirStr = System.getProperty("java.io.tmpdir");
        var tmpDir = new File(tmpDirStr);
        System.out.println("Temp dir: " + tmpDir.getAbsolutePath());
    }

    @Test
    void workingDir() {
        //Linux:
        System.out.printf("System.getenv(\"PWD\")           -> %s%n", System.getenv("PWD"));
        //При запуске из Idea PWD заполняется неправильно

        System.out.printf("System.getProperty(\"user.dir\") -> %s%n", System.getProperty("user.dir"));
    }


}