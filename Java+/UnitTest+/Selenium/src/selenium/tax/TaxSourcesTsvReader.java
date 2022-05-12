package selenium.tax;

import util.ResourceUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class TaxSourcesTsvReader {
    public static List<TaxSource> read(String resource) {
        return ResourceUtil.resourceToStringList(resource).stream()
                .map(line -> line.split("\t"))
                .map(arr -> new TaxSource(
                        new BigDecimal(arr[0]),
                        arr[1],
                        new BigDecimal(arr[2]),
                        LocalDate.parse(arr[3]),
                        getSumPaid(arr[4]),
                        new BigDecimal(arr[5]),
                        arr[6]))
                .toList();
    }

    private static BigDecimal getSumPaid(String value) {
        return value.isBlank() ? BigDecimal.ZERO : new BigDecimal(value);
    }

}
