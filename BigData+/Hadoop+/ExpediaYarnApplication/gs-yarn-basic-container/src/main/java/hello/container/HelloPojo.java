package hello.container;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.yarn.annotation.OnContainerStart;
import org.springframework.yarn.annotation.YarnComponent;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.lang.String.format;

@YarnComponent
public class HelloPojo {

    private static final Log log = LogFactory.getLog(HelloPojo.class);

    @Autowired
    private Configuration configuration;

    @OnContainerStart
    public void publicVoidNoArgsMethod() throws Exception {
        configuration.setBoolean("fs.hdfs.impl.disable.cache", true);

        FileSystem fs = FileSystem.get(configuration);
        Path sampleCsv = new Path("/expedia/train.csv");

        try (FSDataInputStream sampleIs = fs.open(sampleCsv);
             InputStreamReader isr = new InputStreamReader(sampleIs);
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            long lineNum = 0;
            long skippedNotBooking = 0;
            while ((line = br.readLine()) != null) {
                lineNum += 1;
                log.info(format("Process line #%d", lineNum));
                log.debug(format("Line #%d: %s", lineNum, line));
                if (lineNum == 1) {
                    log.debug("Skip header");
                    continue;
                }
                Visit visit;
                try {
                    visit = CsvParser.parseVisit(line);
                } catch (Exception e) {
                    log.error(format("Parse error on line #%d: %s", lineNum, line), e);
                    continue;
                }
                if (!visit.isBooking()) {
                    skippedNotBooking += 1;
                    log.debug("Skip not booked line #" + lineNum);
                }
            }
            log.info(format("Finished processing %d lines. Skipped not booking %d lines",
                    lineNum - 1, skippedNotBooking));
        }
    }

}
