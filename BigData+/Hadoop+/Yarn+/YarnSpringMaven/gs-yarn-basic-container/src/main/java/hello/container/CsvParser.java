package hello.container;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;

class CsvParser {
    private static final Log log = LogFactory.getLog(CsvParser.class);
    private static final String CSV_DELIMITER = ",";

    static Visit parseVisit(String line) {
        String[] fields = line.split(CSV_DELIMITER);
        log.debug("Parse line: " + Arrays.deepToString(fields));

        String dateTime = parseString(fields[0]);
        Integer siteName = parseInt(fields[1]);
        Integer posaContinent = parseInt(fields[2]);
        Integer userLocationCountry = parseInt(fields[3]);
        Integer userLocationRegion = parseInt(fields[4]);
        Integer userLocationCity = parseInt(fields[5]);
        Double origDestinationDistance = parseDouble(fields[6]);
        Integer userId = parseInt(fields[7]);
        Boolean isMobile = parseBoolean(fields[8]);
        Boolean isPackage = parseBoolean(fields[9]);
        Integer channel = parseInt(fields[10]);
        String srchCi = parseString(fields[11]);
        String srchCo = parseString(fields[12]);
        Integer srchAdultsCnt = parseInt(fields[13]);
        Integer srchChildrenCnt = parseInt(fields[14]);
        Integer srchRmCnt = parseInt(fields[15]);
        Integer srchDestinationId = parseInt(fields[16]);
        Integer srchDestinationTypeId = parseInt(fields[17]);
        Boolean isBooking = parseBoolean(fields[18]);
        Long cnt = parseLong(fields[19]);
        Integer hotelContinent = parseInt(fields[20]);
        Integer hotelCountry = parseInt(fields[21]);
        Integer hotelMarket = parseInt(fields[22]);
        Integer hotelCluster = parseInt(fields[23]);

        return new Visit(
                dateTime,
                siteName,
                posaContinent,
                userLocationCountry,
                userLocationRegion,
                userLocationCity,
                origDestinationDistance,
                userId,
                isMobile,
                isPackage,
                channel,
                srchCi,
                srchCo,
                srchAdultsCnt,
                srchChildrenCnt,
                srchRmCnt,
                srchDestinationId,
                srchDestinationTypeId,
                isBooking,
                cnt,
                hotelContinent,
                hotelCountry,
                hotelMarket,
                hotelCluster
        );
    }

    private static Integer parseInt(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return Integer.parseInt(s);
    }

    private static Double parseDouble(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return Double.parseDouble(s);
    }

    private static Boolean parseBoolean(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return "1".equals(s);
    }

    private static Long parseLong(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return Long.parseLong(s);
    }

    private static String parseString(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return s;
    }
}
