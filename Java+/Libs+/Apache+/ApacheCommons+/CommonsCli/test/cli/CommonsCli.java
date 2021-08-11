package cli;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Создаем объект с заданными датой и временем.
 */
class CommonsCli {

    @Test
    void parse() throws ParseException {
        var args = new String[]{"-t", "-d", "abc"};

        var options = new Options();
        options.addOption("t", false, "display current time");
        options.addOption("d", true, "enter string");

        CommandLineParser parser = new DefaultParser();
        var cmd = parser.parse(options, args);
        assertTrue(cmd.hasOption("t"));
        assertTrue(cmd.hasOption("d"));
        assertThat(cmd.getOptionValue("d"), equalTo("abc"));
    }

    @Test
    void printHelp() {
        var options = new Options();
        options.addOption("t", false, "display current time");
        options.addOption("d", true, "enter string");

        var formatter = new HelpFormatter();
        formatter.printHelp("ant", options);
    }
}
