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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@YarnComponent
public class DataProcessor {

    private static final Log log = LogFactory.getLog(DataProcessor.class);

    @Autowired
    private Configuration configuration;

    @OnContainerStart
    public void publicVoidNoArgsMethod() throws Exception {
        configuration.setBoolean("fs.hdfs.impl.disable.cache", true);

        FileSystem fs = FileSystem.get(configuration);
        Path sampleCsv = new Path("/expedia/sample_100_000.csv");

        int beginHotelCountry = 32;
        int endHotelCountry = 70;
        int beginHotelMarket = 126;
        int endHotelMarket = 680;

        //TODO not close FSDataInputStream
        try (FSDataInputStream sampleIs = fs.open(sampleCsv);
             InputStreamReader isr = new InputStreamReader(sampleIs);
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            long lineNum = 0;
            long skippedNotBooking = 0;
            long skippedHotelCountry = 0;
            long skippedHotelMarket = 0;
            int logEveryLines = 100000;
            List<Visit> filteredVisits = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                lineNum += 1;
                if (lineNum % logEveryLines == 0) {
                    log.info(format("Process line #%d", lineNum));
                }
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
                    continue;
                }
                Integer hotelCountry = visit.getHotelCountry();
                if (hotelCountry < beginHotelCountry || hotelCountry > endHotelCountry) {
                    skippedHotelCountry += 1;
                    log.debug(format("Skip hotel country #%d: %d", lineNum, hotelCountry));
                    continue;
                }
                Integer hotelMarket = visit.getHotelMarket();
                if (hotelMarket < beginHotelMarket || hotelMarket > endHotelMarket) {
                    skippedHotelMarket += 1;
                    log.debug(format("Skip hotel market #%d: %d", lineNum, hotelMarket));
                    continue;
                }
                filteredVisits.add(visit);
            }
            long lineNumWithoutHeader = lineNum - 1;
            log.info(format("Finished loading %d lines", lineNumWithoutHeader));
            log.info(format("Filtered visits: %d", filteredVisits.size()));
            log.info(format("Skipped not booking %d lines", skippedNotBooking));
            log.info(format("Skipped hotel country %d lines", skippedHotelCountry));
            log.info(format("Skipped hotel market %d lines", skippedHotelMarket));
            long controlDiff = lineNumWithoutHeader - (filteredVisits.size() + skippedNotBooking
                    + skippedHotelCountry + skippedHotelMarket);
            log.info(format("Control diff: %d", controlDiff));

            Map<String, List<Visit>> groupedVisits = new HashMap<>();
            for (Visit visit : filteredVisits) {
                try {
                    String key = format("%d - %d", visit.getHotelCountry(), visit.getHotelMarket());
                    if (groupedVisits.containsKey(key)) {
                        List<Visit> group = groupedVisits.get(key);
                        group.add(visit);
                    } else {
                        List<Visit> group = new ArrayList<>();
                        group.add(visit);
                        groupedVisits.put(key, group);
                    }
                } catch (Exception e) {
                    log.error("Error while processing " + visit);
                }
            }
            Map<String, Integer> countVisits = new HashMap<>();
            for (String key : groupedVisits.keySet()) {
                List<Visit> group = groupedVisits.get(key);
                countVisits.put(key, group.size());
            }
            countVisits.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(3)
                    .forEach(tuple -> log.info(format("Top: %s - %d", tuple.getKey(), tuple.getValue())));
            log.info("Processing finished");
        }
    }

}
